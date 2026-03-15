package com.tranphanquocan.bookingks.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import com.tranphanquocan.bookingks.ui.components.HotelCard

@Composable
fun HomeScreen(hotels: List<Hotel>) {

    Column {

        SearchBar()

        LazyColumn {
            items(hotels) { hotel ->
                HotelCard(hotel)
            }
        }

    }

}

@Composable
fun HotelItem(x0: Int) {
    TODO("Not yet implemented")
}