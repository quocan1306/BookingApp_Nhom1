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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.ui.theme.ButtonBlue
import com.tranphanquocan.bookingks.ui.theme.LightGray
import com.tranphanquocan.bookingks.ui.theme.PrimaryBlue

@Composable
fun EditProfileFieldScreen(
    navController: NavController,
    fieldType: String,
    currentValue: String
) {
    val title = when (fieldType) {
        "name" -> "Chỉnh sửa tên"
        "gender" -> "Chỉnh sửa giới tính"
        "birthdate" -> "Chỉnh sửa ngày sinh"
        "passport" -> "Chỉnh sửa hộ chiếu"
        "email" -> "Chỉnh sửa email"
        else -> "Chỉnh sửa thông tin"
    }

    val label = when (fieldType) {
        "name" -> "Tên"
        "gender" -> "Giới tính"
        "birthdate" -> "Ngày sinh"
        "passport" -> "Thông tin hộ chiếu"
        "email" -> "Địa chỉ email"
        else -> "Thông tin"
    }

    val keyboardType = if (fieldType == "email") {
        KeyboardType.Email
    } else {
        KeyboardType.Text
    }

    val normalizedValue = when (currentValue) {
        "empty", "Chọn giới tính", "Nhập ngày sinh của bạn", "Chưa cung cấp" -> ""
        else -> currentValue
    }

    var value by rememberSaveable { mutableStateOf(normalizedValue) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
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
                    modifier = Modifier.clickable { navController.popBackStack() }
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(label) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val finalValue = when {
                        value.isNotBlank() -> value
                        fieldType == "gender" -> "Chọn giới tính"
                        fieldType == "birthdate" -> "Nhập ngày sinh của bạn"
                        fieldType == "passport" -> "Chưa cung cấp"
                        else -> currentValue
                    }

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(fieldType, finalValue)

                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBlue,
                    contentColor = Color.White
                )
            ) {
                Text("Lưu")
            }
        }
    }
}