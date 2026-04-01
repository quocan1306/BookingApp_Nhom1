package com.tranphanquocan.bookingks.ui.screen.register
import android.widget.Toast
import com.tranphanquocan.bookingks.ui.theme.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape

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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    onBackToHome: () -> Unit,
    navController: NavController,
    viewModel: AuthViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "Đăng ký",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(3.dp, BorderYellow, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("User Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldBookingColors()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldBookingColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Mật khẩu") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldBookingColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                placeholder = { Text("Nhập lại mật khẩu") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldBookingColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            Button(
                onClick = {

                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(context, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (password.length < 6) {
                        Toast.makeText(context, "Mật khẩu phải >= 6 ký tự", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (password != confirm) {
                        Toast.makeText(context, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModel.register(email, password, name) {
                        if (it) {
                            Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()

                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBlue
                )
            ) {
                Text("Đăng ký")
            }


        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Quay lại đăng nhập",
            color = Color.Yellow,
            modifier = Modifier.clickable {
                onBackToLogin()
            }
        )
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
    unfocusedBorderColor = Color.Transparent
)

