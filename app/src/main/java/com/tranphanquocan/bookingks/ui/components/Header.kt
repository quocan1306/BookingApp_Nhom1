package com.tranphanquocan.bookingks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.ui.theme.AccentBlue
import com.tranphanquocan.bookingks.ui.theme.BorderYellow
import com.tranphanquocan.bookingks.ui.theme.ButtonBlue
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
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
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
    var showGuestSheet by remember { mutableStateOf(false) }

    var rooms by remember { mutableIntStateOf(1) }
    var adults by remember { mutableIntStateOf(2) }
    var children by remember { mutableIntStateOf(0) }

    val childAges = remember { mutableStateListOf<String>() }

    var showAgeDialog by remember { mutableStateOf(false) }
    var editingChildIndex by remember { mutableIntStateOf(-1) }

    val ageOptions = remember {
        listOf("< 1 tuổi") + (1..17).map { "$it tuổi" }
    }

    fun syncChildAges() {
        while (childAges.size < children) {
            childAges.add("17 tuổi")
        }
        while (childAges.size > children) {
            childAges.removeAt(childAges.lastIndex)
        }
    }

    val guestText = remember(rooms, adults, children) {
        "$rooms phòng · $adults người lớn · $children trẻ em"
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(
                3.dp,
                BorderYellow,
                RoundedCornerShape(12.dp)
            )
    ) {
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

        SearchItem(
            icon = Icons.Default.DateRange,
            text = dateText,
            onClick = { showDatePicker = true }
        )

        HorizontalDivider()

        SearchItem(
            icon = Icons.Default.Person,
            text = guestText,
            onClick = { showGuestSheet = true }
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
//show lịch
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
                            dateText = sdf.format(Date(selectedDateMillis))
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

    //show khách
    if (showGuestSheet) {
        ModalBottomSheet(
            onDismissRequest = { showGuestSheet = false },

            modifier = Modifier.navigationBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Đóng",
                            modifier = Modifier.clickable { showGuestSheet = false }
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Chọn phòng và khách",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                item {
                    GuestCounterRow(
                        title = "Phòng",
                        count = rooms,
                        onDecrease = {
                            if (rooms > 1) rooms--
                        },
                        onIncrease = {
                            rooms++
                        },
                        canDecrease = rooms > 1
                    )
                }

                item {
                    GuestCounterRow(
                        title = "Người lớn",
                        count = adults,
                        onDecrease = {
                            if (adults > 1) adults--
                        },
                        onIncrease = {
                            adults++
                        },
                        canDecrease = adults > 1
                    )
                }

                item {
                    GuestCounterRow(
                        title = "Trẻ em",
                        subtitle = "0 - 17 tuổi",
                        count = children,
                        onDecrease = {
                            if (children > 0) {
                                children--
                                syncChildAges()
                            }
                        },
                        onIncrease = {
                            children++
                            syncChildAges()
                        },
                        canDecrease = children > 0
                    )
                }

                if (children > 0) {
                    items(children) { index ->
                        key(index) {
                            ChildAgeField(
                                label = "Trẻ em ${index + 1} *",
                                value = childAges.getOrElse(index) { "17 tuổi" },
                                onClick = {
                                    editingChildIndex = index
                                    showAgeDialog = true
                                }
                            )
                        }
                    }
                }

                item {
                    Button(
                        onClick = { showGuestSheet = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentBlue
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Áp dụng",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }

    if (showAgeDialog && editingChildIndex >= 0) {
        AlertDialog(
            onDismissRequest = { showAgeDialog = false },
            title = {
                Text(
                    text = "Tuổi của trẻ em",
                    fontSize = 22.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    ageOptions.forEach { age ->
                        Text(
                            text = age,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (editingChildIndex in childAges.indices) {
                                        childAges[editingChildIndex] = age
                                    }
                                    showAgeDialog = false
                                }
                                .padding(vertical = 12.dp),
                            color = if (
                                editingChildIndex in childAges.indices &&
                                childAges[editingChildIndex] == age
                            ) Color.Black else Color.Gray,
                            fontSize = 18.sp
                        )
                        HorizontalDivider()
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showAgeDialog = false }
                ) {
                    Text("OK", color = AccentBlue)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showAgeDialog = false }
                ) {
                    Text("HỦY", color = AccentBlue)
                }
            }
        )
    }
}

//số lượng khách, phòng và trẻ em
@Composable
private fun GuestCounterRow(
    title: String,
    count: Int,
    subtitle: String? = null,
    canDecrease: Boolean,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black
            )

            if (subtitle != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFBDBDBD),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(enabled = canDecrease) { onDecrease() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Giảm",
                    tint = if (canDecrease) AccentBlue else Color.LightGray
                )
            }

            Text(
                text = count.toString(),
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onIncrease() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tăng",
                    tint = AccentBlue
                )
            }
        }
    }
}

@Composable
private fun ChildAgeField(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = buildAnnotatedString {
                append(label.replace(" *", "")) // phần chữ
                append(" ")
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFBDBDBD),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Chọn tuổi"
            )
        }
    }
}

@Composable
fun TabItem(
    icon: ImageVector,
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
            .clickable { onClick() }
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
    var selectedTab by remember { mutableIntStateOf(0) }

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
        ) {
            selectedTab = 0
        }
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
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentBlue
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Xin chào, $userName",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        } else {
            Button(
                onClick = { onLoginClick() },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentBlue
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Đăng nhập",
                    color = Color.White
                )
            }
        }
    }
}