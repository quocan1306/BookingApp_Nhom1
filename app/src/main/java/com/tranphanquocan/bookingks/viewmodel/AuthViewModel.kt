package com.tranphanquocan.bookingks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranphanquocan.bookingks.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val repo = AuthRepository()
    fun login(email: String, password :String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val result = repo.login(email, password)
            onResult(result.isSuccess)
        }
    }
    fun register(
        email: String,
        password: String,
        name: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            val result = repo.register(email, password, name)
            onResult(result.isSuccess)
        }
    }

    fun isLoggedIn() = repo.getCurrentUser() != null
    fun logout() = repo.logOut()
}