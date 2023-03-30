package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Dao
interface EventDatabaseDao {
    @Query("SELECT * from event")
    fun getAll(): LiveData<List<Event>>

    @Query("SELECT * from event where deptId = :id")
    fun getByDeptId(id: String): LiveData<List<Event>>

    @Query("SELECT * from event where saved = true")
    fun getBySaved(): LiveData<List<Event>>

    @Update
    suspend fun update(event:Event)
}

@Database(entities = [Event::class], version = 1)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDatabaseDao

    companion object {
        private var INSTANCE: EventDatabase? = null
        fun getInstance(context: Context): EventDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventDatabase::class.java,
                        "event_database"
                    )
                        .createFromAsset("events.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

class EventRepository(private val eventDatabaseDao: EventDatabaseDao) {

    val readAllData: LiveData<List<Event>> = eventDatabaseDao.getAll()

    val readAllUpdate: LiveData<List<Event>> = eventDatabaseDao.getBySaved()

    suspend fun updateEvent(event: Event) {
        eventDatabaseDao.update(event)
    }
}

class EventViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Event>>
    var readAllUpdate: LiveData<List<Event>>

    private val repository: EventRepository

    init {
        val eventDao = EventDatabase.getInstance(application).eventDao()
        repository = EventRepository(eventDao)
        readAllData = repository.readAllData
        readAllUpdate = repository.readAllUpdate

    }

    fun bookmarkEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            event.saved = true
            repository.updateEvent(event = event)
        }
    }

    fun removeEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            event.saved = false
            repository.updateEvent(event = event)
        }
    }

}

class EventViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}