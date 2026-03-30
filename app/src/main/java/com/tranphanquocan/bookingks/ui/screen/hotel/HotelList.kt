package com.tranphanquocan.bookingks.ui.screen.hotel

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.data.model.Hotel
import com.tranphanquocan.bookingks.data.model.SavedHotelItem
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.ui.theme.BorderYellow
import com.tranphanquocan.bookingks.ui.theme.ButtonBlue
import com.tranphanquocan.bookingks.ui.theme.LightGray

@Composable
fun HotelListScreen(
    navController: NavController,
    location: String,
    checkIn: String,
    checkOut: String,
    hotels: List<Hotel>
) {
    var sortType by remember { mutableStateOf("asc") }

    val sortedHotels = when (sortType) {
        "asc" -> hotels.sortedBy { parsePrice(it.newPrice) }
        "desc" -> hotels.sortedByDescending { parsePrice(it.newPrice) }
        else -> hotels
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HotelListHeader(
                location = location,
                checkIn = checkIn,
                checkOut = checkOut,
                onBackClick = { navController.popBackStack() }
            )

            FilterBar(
                sortType = sortType,
                onSortChange = { sortType = it }
            )

            Text(
                text = "${sortedHotels.size} chỗ nghỉ",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                items(sortedHotels) { hotel ->
                    HotelListItem(
                        hotel = hotel,
                        checkIn = checkIn,
                        checkOut = checkOut,
                        onCardClick = {
                            val safeCheckIn = if (checkIn.isBlank()) "empty" else checkIn
                            val safeCheckOut = if (checkOut.isBlank()) "empty" else checkOut

                            navController.navigate(
                                "hotel_detail/" +
                                        Uri.encode(hotel.name) + "/" +
                                        Uri.encode(hotel.location) + "/" +
                                        Uri.encode(safeCheckIn) + "/" +
                                        Uri.encode(safeCheckOut) + "/" +
                                        hotel.image + "/" +
                                        Uri.encode(hotel.tag) + "/" +
                                        Uri.encode(hotel.oldPrice) + "/" +
                                        Uri.encode(hotel.newPrice)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HotelListHeader(
    location: String,
    checkIn: String,
    checkOut: String,
    onBackClick: () -> Unit
) {
    val headerText = if (checkIn.isNotBlank() && checkOut.isNotBlank()) {
        "$location · $checkIn - $checkOut"
    } else {
        location
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ButtonBlue)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            border = BorderStroke(3.dp, BorderYellow)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { onBackClick() }
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = headerText,
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun FilterBar(
    sortType: String,
    onSortChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.clickable { expanded = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.FilterList,
                contentDescription = "Giá"
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = if (sortType == "asc") "Giá: Thấp → Cao" else "Giá: Cao → Thấp",
                fontSize = 16.sp
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Giá thấp → cao") },
                onClick = {
                    onSortChange("asc")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("Giá cao → thấp") },
                onClick = {
                    onSortChange("desc")
                    expanded = false
                }
            )
        }
    }

    HorizontalDivider(color = Color(0xFFE0E0E0))
}

@Composable
private fun HotelListItem(
    hotel: Hotel,
    checkIn: String,
    checkOut: String,
    onCardClick: () -> Unit
) {
    val isSaved = UserState.isHotelSaved(
        name = hotel.name,
        location = hotel.location,
        checkIn = checkIn,
        checkOut = checkOut
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = hotel.image),
                contentDescription = hotel.name,
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = hotel.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = if (isSaved) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isSaved) Color.Red else Color.Black,
                        modifier = Modifier.clickable {
                            UserState.toggleSavedHotel(
                                SavedHotelItem(
                                    name = hotel.name,
                                    location = hotel.location,
                                    tag = hotel.tag,
                                    oldPrice = hotel.oldPrice,
                                    newPrice = hotel.newPrice,
                                    image = hotel.image,
                                    checkIn = checkIn,
                                    checkOut = checkOut
                                )
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = hotel.location,
                    color = Color.Gray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFF15A34A),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = hotel.tag,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = hotel.oldPrice,
                    color = Color.Red,
                    fontSize = 16.sp
                )

                Text(
                    text = hotel.newPrice,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Đã bao gồm thuế và phí",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

private fun parsePrice(price: String): Long {
    return price
        .replace("VND", "")
        .replace(".", "")
        .replace(",", "")
        .trim()
        .toLongOrNull() ?: Long.MAX_VALUE
}