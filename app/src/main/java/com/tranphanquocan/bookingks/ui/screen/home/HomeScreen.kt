package com.tranphanquocan.bookingks.ui.screen.home

import DestinationCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.data.model.Destinations
import com.tranphanquocan.bookingks.data.model.Hotel
import com.tranphanquocan.bookingks.ui.components.BottomNavigationBar
import com.tranphanquocan.bookingks.ui.components.Header
import com.tranphanquocan.bookingks.ui.components.HotelCard
import com.tranphanquocan.bookingks.ui.components.SearchBox
import com.tranphanquocan.bookingks.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    hotels: List<Hotel>,
    destinations: List<Destinations>,
    navController: NavController,
    viewModel: AuthViewModel
) {
    var selectedLocation by rememberSaveable { mutableStateOf("") }
    var selectedCheckIn by rememberSaveable { mutableStateOf("") }
    var selectedCheckOut by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Header(
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                SearchBox(
                    onSearchClick = { location, checkIn, checkOut ->
                        selectedLocation = location
                        selectedCheckIn = checkIn
                        selectedCheckOut = checkOut

                        navController.navigate(
                            "hotel_list/" +
                                    android.net.Uri.encode(location) + "/" +
                                    android.net.Uri.encode(checkIn) + "/" +
                                    android.net.Uri.encode(checkOut)
                        )
                    }
                )
            }

            item {
                SectionTitle(
                    title = "Ưu đãi",
                    subtitle = "Ưu đãi dành cho bạn"
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(hotels) { hotel ->
                        HotelCard(
                            hotel = hotel,
                            onClick = {
                                val safeCheckIn =
                                    if (selectedCheckIn.isBlank()) "empty" else selectedCheckIn
                                val safeCheckOut =
                                    if (selectedCheckOut.isBlank()) "empty" else selectedCheckOut

                                navController.navigate(
                                    "hotel_detail/" +
                                            android.net.Uri.encode(hotel.name) + "/" +
                                            android.net.Uri.encode(hotel.location) + "/" +
                                            android.net.Uri.encode(safeCheckIn) + "/" +
                                            android.net.Uri.encode(safeCheckOut)
                                )
                            }
                        )
                    }
                }
            }

            item {
                SectionTitle(
                    title = "Du khách cũng đã đặt",
                    subtitle = "Thêm gợi ý cho chuyến đi của bạn trong khoảng thời gian ngày 20 tháng 3 – ngày 22 tháng 3"
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(destinations) { destination ->
                        DestinationCard(
                            destination = destination,
                            onClick = {
                                navController.navigate(
                                    "hotel_list_by_location/${android.net.Uri.encode(destination.location)}"
                                )
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SectionTitle(
    title: String,
    subtitle: String
) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}