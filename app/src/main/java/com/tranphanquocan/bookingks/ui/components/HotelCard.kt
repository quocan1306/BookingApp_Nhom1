package com.tranphanquocan.bookingks.ui.components

import android.text.Highlights
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranphanquocan.bookingks.data.model.Hotel
import com.tranphanquocan.bookingks.ui.theme.AccentBlue


@Composable
fun HotelCard(hotel: Hotel) {

    Card(
        modifier = Modifier
            .padding(12.dp)
            .width(260.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column {

            // IMAGE
            Image(
                painter = painterResource(id = hotel.image),
                contentDescription = hotel.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                // HOTEL NAME
                Text(
                    text = hotel.name,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // RATING ROW
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AccentBlue)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = hotel.rating.toString(),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "(${hotel.reviewCount} đánh giá)",
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // LOCATION
                Text(
                    text = hotel.location,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // TAG
                Box(
                    modifier = Modifier
                        .background(Color(0xFF2E7D32), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = hotel.tag,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // PRICE
                Column {

                    Text(
                        text = hotel.oldPrice,
                        fontSize = 13.sp,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )

                    Text(
                        text = hotel.newPrice,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

            }
        }
    }
}
