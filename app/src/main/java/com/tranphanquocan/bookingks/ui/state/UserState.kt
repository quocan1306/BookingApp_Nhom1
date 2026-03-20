package com.tranphanquocan.bookingks.ui.state

import androidx.compose.runtime.mutableStateOf

object UserState {
    var isLoggedIn = mutableStateOf(false)
    var userName = mutableStateOf("")
}