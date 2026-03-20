package com.tranphanquocan.bookingks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.ui.theme.AccentBlue
import com.tranphanquocan.bookingks.ui.theme.ButtonBlue
import com.tranphanquocan.bookingks.ui.theme.HeaderBlue

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun Header(
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(ButtonBlue)
            .fillMaxWidth()

    ) {
        TopBar(onLoginClick)
        CategoryTabs()
    }
}
@Composable
fun SearchItem(
    icon: ImageVector,
    text: String,
    background: Boolean = false,
    onClick: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (background) Color(0xFFF2F2F2) else Color.Transparent
            )
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(icon, contentDescription = "")

        Spacer(modifier = Modifier.width(12.dp))

        Text(text)

    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox() {

    var location by remember { mutableStateOf("") }
    var dateText by remember { mutableStateOf("Chọn ngày") }

    var showDatePicker by remember { mutableStateOf(false) }

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

        //  SEARCH TEXT
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "")
            },
            placeholder = {
                Text(
                    "Nhập địa điểm bạn muốn đến",
                    color = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        HorizontalDivider()

        // DATE PICKER
        SearchItem(
            icon = Icons.Default.DateRange,
            text = dateText,
            onClick = { showDatePicker = true }
        )

        HorizontalDivider()

        // GUEST
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
                containerColor = ButtonBlue
            )
        ) {
            Text("Tìm")
        }
    }

    // DATE PICKER DIALOG
    if (showDatePicker) {

        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {

                        val selectedDateMillis = datePickerState.selectedDateMillis

                        if (selectedDateMillis != null) {

                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val formattedDate = sdf.format(Date(selectedDateMillis))

                            dateText = formattedDate
                        }

                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
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
            .padding(horizontal = 16.dp, vertical = 16.dp),
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
fun TopBar(
    onLoginClick: () -> Unit = {}
) {

    val isLoggedIn = UserState.isLoggedIn.value
    val userName = UserState.userName.value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Booking_KS",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        if (isLoggedIn) {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentBlue
                )
            ) {
                Text(text = "Xin chào, $userName",
                    color = Color.White,
                    fontSize = 14.sp)
            }

        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    onClick = {onLoginClick()},
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentBlue
                    )
                ) {
                    Text("Đăng nhập",
                    color = Color.White)
                }
            }
        }
    }
}