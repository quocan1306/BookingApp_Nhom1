package com.tranphanquocan.bookingks.ui.screen.home

import DestinationCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavigationBar()
        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(
                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                end = padding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = padding.calculateBottomPadding()
            )
        ) {

            // HEADER (TopBar + Tabs) - FIXED
            Header(
                onLoginClick = {
                    navController.navigate("login")
                }
            )

            // CONTENT SCROLL
            LazyColumn {

                // SEARCH BOX SECTION (SCROLL)
                item {
                    SearchBox()
                }

                // SECTION ƯU ĐÃI
                item {
                    Text(
                        text = "Ưu đãi",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Text(
                        text = "Ưu đãi dành cho bạn",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        items(hotels) {
                            HotelCard(it)
                        }
                    }
                }

                // SECTION DU KHÁCH ĐÃ ĐẶT
                item {
                    Text(
                        text = "Du khách cũng đã đặt",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Text(
                        text = "Thêm gợi ý cho chuyến đi của bạn trong khoảng thời gian ngày 20 tháng 3 – ngày 22 tháng 3",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {

                        items(destinations) { destination ->
                            DestinationCard(destination)
                        }

                    }
                }
            }
        }
    }
}