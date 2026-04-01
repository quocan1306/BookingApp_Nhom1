package com.tranphanquocan.bookingks.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.ui.theme.ButtonBlue
import com.tranphanquocan.bookingks.ui.theme.LightGray
import com.tranphanquocan.bookingks.ui.theme.PrimaryBlue

@Composable
fun PaymentMethodScreen(
    navController: NavController
) {
    val savedCard = UserState.paymentCard.value

    var cardHolderName by rememberSaveable {
        mutableStateOf(savedCard?.cardHolderName ?: "")
    }
    var cardNumber by rememberSaveable {
        mutableStateOf(savedCard?.cardNumber ?: "")
    }
    var expiryDate by rememberSaveable {
        mutableStateOf(savedCard?.expiryDate ?: "")
    }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var successMessage by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            PaymentTopBar(
                title = "Phương thức thanh toán",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGray)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        when {
                            cardHolderName.isBlank() -> {
                                errorMessage = "Vui lòng nhập tên thẻ"
                                successMessage = ""
                            }
                            cardNumber.isBlank() -> {
                                errorMessage = "Vui lòng nhập số thẻ"
                                successMessage = ""
                            }
                            expiryDate.isBlank() -> {
                                errorMessage = "Vui lòng nhập ngày hết hạn"
                                successMessage = ""
                            }
                            else -> {
                                UserState.savePaymentCard(
                                    cardHolderName = cardHolderName.trim(),
                                    cardNumber = cardNumber.trim(),
                                    expiryDate = expiryDate.trim()
                                )
                                errorMessage = ""
                                successMessage = "Lưu phương thức thanh toán thành công"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Lưu phương thức thanh toán",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Nhập thông tin thẻ để lưu phương thức thanh toán của bạn.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF555555)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tên thẻ",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = cardHolderName,
                onValueChange = {
                    cardHolderName = it
                    errorMessage = ""
                    successMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Nhập tên chủ thẻ") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Số thẻ",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = cardNumber,
                onValueChange = {
                    cardNumber = it
                    errorMessage = ""
                    successMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Nhập số thẻ") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Ngày hết hạn",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = expiryDate,
                onValueChange = {
                    expiryDate = it
                    errorMessage = ""
                    successMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("MM/YY") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (successMessage.isNotBlank()) {
                Text(
                    text = successMessage,
                    color = Color(0xFF16A34A),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun PaymentTopBar(
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