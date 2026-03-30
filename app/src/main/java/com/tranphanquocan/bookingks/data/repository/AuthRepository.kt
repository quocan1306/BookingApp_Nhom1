package com.tranphanquocan.bookingks.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tranphanquocan.bookingks.ui.state.UserState
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result : AuthResult
            = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user?.uid?:
            throw Exception("User not found"))

        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        UserState.isLoggedIn.value = false
        UserState.userName.value = ""
    }
    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}