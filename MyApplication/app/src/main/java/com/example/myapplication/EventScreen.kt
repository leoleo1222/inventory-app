package com.example.myapplication
import android.app.Application
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.launch

@Entity(tableName = "event")
data class Event(
    @PrimaryKey val id: Int, val title: String, val deptId: String, var saved: Boolean
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(snackbarHostState: SnackbarHostState ,deptId: String?) {

    val context = LocalContext.current
    val eventViewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(context.applicationContext as Application)
    )

    val events by eventViewModel.readAllData.observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        items(events.filter{it.deptId == deptId}) { event ->
            ListItem(
                headlineText = { Text(event.title) },
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            eventViewModel.bookmarkEvent(event)

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    "Event has been added to itinerary."
                                )
                            }
                        }
                    )
                }
            )
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventPreview() {
//    InfoDayTheme {
//        EventScreen()
//    EventScreen("COMP")
//    }
}


