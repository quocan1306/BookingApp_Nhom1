package com.tranphanquocan.bookingks.ui.screen.login
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import com.tranphanquocan.bookingks.ui.theme.*


import androidx.compose.material3.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
//    onLoginSuccess: () -> Unit,
    navController: NavController,
    viewModel: AuthViewModel,
    onBackToHome: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // TITLE
        Text(
            "Booking_KS",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // CARD FORM
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(3.dp, BorderYellow, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldBookingColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // PASSWORD
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Mật khẩu") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldBookingColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Quên mật khẩu?",
                color = ButtonBlue,
                modifier = Modifier.clickable {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.login(email, password) {
                    if (it) {
                        //email firebase
                        UserState.isLoggedIn.value = true
                        val user = FirebaseAuth.getInstance().currentUser

                        UserState.userName.value =
                            user?.displayName ?: user?.email ?: ""

                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    else{
                        Toast.makeText(navController.context,"Login Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            })
            {   
                Text("Đăng nhập")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Chưa có tài khoản? ", color = Color.White)
            Text(
                "Đăng ký",
                color = Color.Yellow,
                modifier = Modifier.clickable {
//                    onNavigateToRegister()
                }
            )
        }
        Text(
            text = "← Quay lại trang chủ",
            color = Color.Yellow,
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    onBackToHome()
                }
        )

    }

}
@Composable
fun textFieldBookingColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = LightGray,
    unfocusedContainerColor = LightGray,
    focusedBorderColor = Color.Transparent,
    unfocusedBorderColor = Color.Transparent,
    cursorColor = Color.Black
)