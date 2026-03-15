package com.tranphanquocan.bookingks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun HotelCard(hotel: Hotel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column {

            Image(
                painter = rememberAsyncImagePainter(hotel.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Text(hotel.name)

            Text(hotel.location)

            Text("⭐ ${hotel.rating}   $${hotel.price}/night")

        }

    }

}

private fun ColumnScope.rememberAsyncImagePainter(image: Any): Painter {
    TODO("Not yet implemented")
}
