package com.tranphanquocan.bookingks.ui.components

// 🔹 IMPORT
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    // 🔥 Lấy route hiện tại
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    NavigationBar {

        // 🏠 HOME
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "") },
            label = { Text("Trang chủ") },
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo("home")
                    launchSingleTop = true
                }
            }
        )

        // 🎒 TRIP
        NavigationBarItem(
            icon = { Icon(Icons.Default.Luggage, contentDescription = "") },
            label = { Text("Chuyến đi") },
            selected = currentRoute == "trip",
            onClick = {
                navController.navigate("trip") {
                    launchSingleTop = true
                }
            }
        )

        // 👤 PROFILE
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "") },
            label = { Text("Tài khoản") },
            selected = currentRoute == "profile",
            onClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                }
            }
        )
    }
}