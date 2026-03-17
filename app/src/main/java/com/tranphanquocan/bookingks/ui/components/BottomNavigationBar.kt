package com.tranphanquocan.bookingks.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun BottomNavigationBar() {

    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "") },
            label = { Text("Trang chủ") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Luggage, contentDescription = "") },
            label = { Text("Chuyến đi") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "") },
            label = { Text("Tài khoản") },
            selected = selectedItem == 4,
            onClick = { selectedItem = 4 }
        )

    }
}