package com.tranphanquocan.bookingks.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
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

    suspend fun register(email: String, password: String, name: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            val user = result.user ?: throw Exception("User null")

            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()

            user.updateProfile(profileUpdates).await()

            Result.success(user.uid)
        } catch (e: Exception) {
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