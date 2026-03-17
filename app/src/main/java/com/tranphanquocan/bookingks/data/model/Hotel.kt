package com.tranphanquocan.bookingks.data.model

data class Hotel(

    val name: String,
    val location: String,
    val rating: Double,
    val reviewCount: Int,

    val tag: String,

    val oldPrice: String,
    val newPrice: String,

    val image: Int

)

