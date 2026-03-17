package com.tranphanquocan.bookingks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tranphanquocan.bookingks.data.model.Destinations
import com.tranphanquocan.bookingks.data.model.Hotel
import com.tranphanquocan.bookingks.ui.screen.home.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val hotels = listOf(

                Hotel(
                    name = "Resort Cam Ranh",
                    location = "Khánh Hòa",
                    rating = 9.2,
                    reviewCount = 120,
                    tag = "Ưu đãi mùa hè",
                    oldPrice = "VND 3.200.000",
                    newPrice = "VND 2.900.000",
                    image = R.drawable.hotelcard_hotel3
                ),

                Hotel(
                    name = "Vinpearl Resort",
                    location = "Nha Trang",
                    rating = 9.0,
                    reviewCount = 540,
                    tag = "Giảm giá 30%",
                    oldPrice = "VND 4.000.000",
                    newPrice = "VND 3.200.000",
                    image = R.drawable.hotelcard_hotel1
                ),

                Hotel(
                    name = "Vinpearl Resort",
                    location = "Nha Trang",
                    rating = 9.0,
                    reviewCount = 540,
                    tag = "Giảm giá 30%",
                    oldPrice = "VND 4.000.000",
                    newPrice = "VND 3.200.000",
                    image = R.drawable.hotelcard_hotel2
                )

            )

            val destinations = listOf(

                Destinations(
                    name = "TP. Hồ Chí Minh",
                    location = "Hồ Chí Minh",
                    image = R.drawable.destinationcard_hochiminhcity
                ),

                Destinations(
                    name = "Hà Nội",
                    location = "Hà Nội",
                    image = R.drawable.destinationcard_hanoi
                ),
                Destinations(
                    name = "Đà Nẵng",
                    location = "Đà Nẵng",
                    image = R.drawable.destinationcard_danang
                )

            )

            HomeScreen(
                hotels = hotels,
                destinations = destinations
            )

        }
    }
}