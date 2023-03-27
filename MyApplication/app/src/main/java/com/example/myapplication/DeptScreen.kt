package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Dept(val name: String, val id: String) {
    companion object {
        val data = listOf(
            Dept("Computer Science", "COMP"),
            Dept("Communication Studies", "COMS")
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeptScreen(navController: NavHostController) {

    LazyColumn {
        items(Dept.data) { dept ->
            ListItem(
                headlineText = { Text(dept.name) },
                //
                modifier = Modifier.clickable {
//                    navController.navigate("event")
                    navController.navigate("event/${dept.id}") },
                //
                leadingContent = {
                    Icon(
                        Icons.Filled.ThumbUp,
                        contentDescription = null
                    )
                }
            )
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeptPreview() {
//    InfoDayTheme {
//        DeptNav(rememberNavController())
//    }
}

@Composable
fun DeptNav(navController: NavHostController, snackbarHostState: SnackbarHostState) {
    NavHost(
        navController = navController,
        startDestination = "dept",
    ) {
        composable("dept") { DeptScreen(navController) }
//        composable("event") { EventScreen("COMP") }
        composable("event/{deptId}") { backStackEntry ->
            EventScreen(snackbarHostState, backStackEntry.arguments?.getString("deptId"))
        }
    }
}



