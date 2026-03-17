package com.tranphanquocan.bookingks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun Header() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF003B95))
    ) {

        TopBar()

        CategoryTabs()

    }

}
@Composable
fun SearchItem(
    icon: ImageVector,
    text: String,
    background: Boolean = false
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (background) Color(0xFFF2F2F2) else Color.Transparent
            )
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(icon, contentDescription = "")

        Spacer(modifier = Modifier.width(12.dp))

        Text(text)

    }

}
@Composable
fun SearchBox() {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(
                3.dp,
                Color(0xFFFFB700),
                RoundedCornerShape(12.dp)
            )
    ) {

        SearchItem(
            icon = Icons.Default.Search,
            text = "Đà Lạt",

        )

        HorizontalDivider()

        SearchItem(
            icon = Icons.Default.DateRange,
            text = "CN, 15 thg 3 - Th 2, 16 thg 3"
        )

        HorizontalDivider()

        SearchItem(
            icon = Icons.Default.Person,
            text = "1 phòng · 2 người lớn · 0 trẻ em"
        )

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF427BF5)
            )
        ) {

            Text("Tìm")

        }

    }

}
@Composable
fun TabItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .border(
                width = if (selected) 2.dp else 0.dp,
                color = Color.White,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            icon,
            contentDescription = "",
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text,
            color = Color.White
        )

    }

}
@Composable
fun CategoryTabs() {

    var selectedTab by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TabItem(
            icon = Icons.Default.Bed,
            text = "Lưu trú",
            selected = selectedTab == 0
        ) { selectedTab = 0 }



    }

}

@Composable
fun TopBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        Text(
            text = "Booking.com",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "",
            tint = Color.White
        )

    }
}