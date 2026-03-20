package com.tranphanquocan.bookingks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tranphanquocan.bookingks.data.model.Destinations
import com.tranphanquocan.bookingks.data.model.Hotel
import com.tranphanquocan.bookingks.ui.navigation.AppNavigation
import com.tranphanquocan.bookingks.ui.screen.home.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}

