package com.tranphanquocan.bookingks.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

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
fun SearchBox(
    onSearchClick: (String, String, String) -> Unit = { _, _, _ -> }
) {
    var location by remember { mutableStateOf("") }

    var dateText by remember { mutableStateOf("Chọn ngày") }
    var showDatePicker by remember { mutableStateOf(false) }
    var checkInDate by remember { mutableStateOf<LocalDate?>(null) }
    var checkOutDate by remember { mutableStateOf<LocalDate?>(null) }

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
            onClick = {
                if (
                    location.isNotBlank() &&
                    checkInDate != null &&
                    checkOutDate != null
                ) {
                    onSearchClick(
                        location,
                        formatShortDate(checkInDate!!),
                        formatShortDate(checkOutDate!!)
                    )
                }
            },
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

    if (showDatePicker) {
        ModalBottomSheet(
            onDismissRequest = { showDatePicker = false },
            modifier = Modifier.navigationBarsPadding()
        ) {
            DateRangeBottomSheet(
                initialCheckIn = checkInDate,
                initialCheckOut = checkOutDate,
                onApply = { startDate, endDate ->
                    checkInDate = startDate
                    checkOutDate = endDate
                    dateText = formatDateRange(startDate, endDate)
                    showDatePicker = false
                },
                onClose = {
                    showDatePicker = false
                }
            )
        }
    }

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

@SuppressLint("NewApi")
@Composable
private fun DateRangeBottomSheet(
    initialCheckIn: LocalDate?,
    initialCheckOut: LocalDate?,
    onApply: (LocalDate, LocalDate) -> Unit,
    onClose: () -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var tempCheckIn by remember { mutableStateOf(initialCheckIn) }
    var tempCheckOut by remember { mutableStateOf(initialCheckOut) }

    val daysOfWeek = listOf("Th 2", "Th 3", "Th 4", "Th 5", "Th 6", "Th 7", "CN")
    val dates = remember(currentMonth) { buildCalendarDays(currentMonth) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {

        Text(
            text = "Chọn",
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(horizontal = 8.dp),
            userScrollEnabled = false
        ) {
            items(dates) { date ->
                CalendarDayItem(
                    date = date,
                    checkInDate = tempCheckIn,
                    checkOutDate = tempCheckOut,
                    onDateClick = { clickedDate ->
                        when {
                            tempCheckIn == null -> {
                                tempCheckIn = clickedDate
                            }

                            tempCheckOut == null && clickedDate.isAfter(tempCheckIn) -> {
                                tempCheckOut = clickedDate
                            }

                            tempCheckOut == null && clickedDate.isEqual(tempCheckIn) -> {
                                tempCheckOut = clickedDate.plusDays(1)
                            }

                            else -> {
                                tempCheckIn = clickedDate
                                tempCheckOut = null
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = when {
                tempCheckIn != null && tempCheckOut != null ->
                    "${formatShortDate(tempCheckIn!!)} - ${formatShortDate(tempCheckOut!!)} (${ChronoUnit.DAYS.between(tempCheckIn, tempCheckOut)} đêm)"
                tempCheckIn != null ->
                    "Đã chọn ngày nhận phòng: ${formatShortDate(tempCheckIn!!)}"
                else -> "Chọn ngày nhận và trả phòng"
            },
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (tempCheckIn != null && tempCheckOut != null) {
                    onApply(tempCheckIn!!, tempCheckOut!!)
                }
            },
            enabled = tempCheckIn != null && tempCheckOut != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentBlue
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Chọn ngày",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun CalendarDayItem(
    date: LocalDate?,
    checkInDate: LocalDate?,
    checkOutDate: LocalDate?,
    onDateClick: (LocalDate) -> Unit
) {
    if (date == null) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .padding(2.dp)
        )
        return
    }

    val isStart = checkInDate != null && date.isEqual(checkInDate)
    val isEnd = checkOutDate != null && date.isEqual(checkOutDate)
    val isInRange = checkInDate != null && checkOutDate != null &&
            date.isAfter(checkInDate) && date.isBefore(checkOutDate)

    val bgColor = when {
        isStart || isEnd -> AccentBlue
        isInRange -> AccentBlue.copy(alpha = 0.2f)
        else -> Color.Transparent
    }

    val textColor = when {
        isStart || isEnd -> Color.White
        else -> Color.Black
    }

    Box(
        modifier = Modifier
            .size(44.dp)
            .padding(2.dp)
            .background(bgColor, RoundedCornerShape(4.dp))
            .clickable { onDateClick(date) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 18.sp
        )
    }
}

private fun buildCalendarDays(month: YearMonth): List<LocalDate?> {
    val firstDay = month.atDay(1)
    val daysInMonth = month.lengthOfMonth()

    val startOffset = when (firstDay.dayOfWeek) {
        DayOfWeek.MONDAY -> 0
        DayOfWeek.TUESDAY -> 1
        DayOfWeek.WEDNESDAY -> 2
        DayOfWeek.THURSDAY -> 3
        DayOfWeek.FRIDAY -> 4
        DayOfWeek.SATURDAY -> 5
        DayOfWeek.SUNDAY -> 6
    }

    val result = mutableListOf<LocalDate?>()

    repeat(startOffset) {
        result.add(null)
    }

    for (day in 1..daysInMonth) {
        result.add(month.atDay(day))
    }

    return result
}

private fun formatShortDate(date: LocalDate): String {
    return "${date.dayOfMonth} thg ${date.monthValue}"
}

private fun formatDateRange(checkIn: LocalDate, checkOut: LocalDate): String {
    return "${formatShortDate(checkIn)} - ${formatShortDate(checkOut)}"
}

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
                append(label.replace(" *", ""))
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