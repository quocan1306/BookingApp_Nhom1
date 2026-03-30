package com.tranphanquocan.bookingks.data.model

data class SavedHotelItem(
    val name: String,
    val location: String,
    val tag: String,
    val oldPrice: String,
    val newPrice: String,
    val image: Int,
    val checkIn: String,
    val checkOut: String
)
