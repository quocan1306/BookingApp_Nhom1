package com.tranphanquocan.bookingks.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.ui.theme.LightGray
import com.tranphanquocan.bookingks.ui.theme.PrimaryBlue

@Composable
fun ChangePasswordScreen(
    navController: NavController
) {
    var currentPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var currentPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    var errorMessage by rememberSaveable { mutableStateOf("") }
    var successMessage by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            ChangePasswordTopBar(
                title = "Đổi mật khẩu",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Tạo mật khẩu mới để bảo vệ tài khoản của bạn.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = currentPassword,
                onValueChange = {
                    currentPassword = it
                    errorMessage = ""
                    successMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Mật khẩu hiện tại") },
                singleLine = true,
                visualTransformation = if (currentPasswordVisible) {
                    androidx.compose.ui.text.input.VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            TextButton(
                onClick = { currentPasswordVisible = !currentPasswordVisible }
            ) {
                Text(if (currentPasswordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    errorMessage = ""
                    successMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Mật khẩu mới") },
                singleLine = true,
                visualTransformation = if (newPasswordVisible) {
                    androidx.compose.ui.text.input.VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            TextButton(
                onClick = { newPasswordVisible = !newPasswordVisible }
            ) {
                Text(if (newPasswordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = ""
                    successMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Xác nhận mật khẩu mới") },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) {
                    androidx.compose.ui.text.input.VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            TextButton(
                onClick = { confirmPasswordVisible = !confirmPasswordVisible }
            ) {
                Text(if (confirmPasswordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (successMessage.isNotBlank()) {
                Text(
                    text = successMessage,
                    color = Color(0xFF16A34A),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    when {
                        currentPassword.isBlank() -> {
                            errorMessage = "Vui lòng nhập mật khẩu hiện tại"
                            successMessage = ""
                        }
                        newPassword.isBlank() -> {
                            errorMessage = "Vui lòng nhập mật khẩu mới"
                            successMessage = ""
                        }
                        confirmPassword.isBlank() -> {
                            errorMessage = "Vui lòng xác nhận mật khẩu mới"
                            successMessage = ""
                        }
                        newPassword.length < 6 -> {
                            errorMessage = "Mật khẩu mới phải có ít nhất 6 ký tự"
                            successMessage = ""
                        }
                        newPassword != confirmPassword -> {
                            errorMessage = "Xác nhận mật khẩu không khớp"
                            successMessage = ""
                        }
                        currentPassword == newPassword -> {
                            errorMessage = "Mật khẩu mới phải khác mật khẩu hiện tại"
                            successMessage = ""
                        }
                        else -> {
                            errorMessage = ""
                            successMessage = "Đổi mật khẩu thành công (giao diện tạm thời)"
                            currentPassword = ""
                            newPassword = ""
                            confirmPassword = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lưu thay đổi")
            }
        }
    }
}

@Composable
private fun ChangePasswordTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryBlue)
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}