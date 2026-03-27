package com.tranphanquocan.bookingks.ui.screen.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.ui.theme.LightGray
import com.tranphanquocan.bookingks.ui.theme.PrimaryBlue
import com.tranphanquocan.bookingks.ui.theme.TextGray

@Composable
fun PersonalInfoScreen(
    navController: NavController
) {
    val backStackEntry = navController.currentBackStackEntry

    val name by backStackEntry
        ?.savedStateHandle
        ?.getStateFlow("name", "")
        ?.collectAsState() ?: remember {
        mutableStateOf("")
    }

    val gender by backStackEntry
        ?.savedStateHandle
        ?.getStateFlow("gender", "")
        ?.collectAsState() ?: remember {
        mutableStateOf("")
    }

    val birthDate by backStackEntry
        ?.savedStateHandle
        ?.getStateFlow("birthdate", "")
        ?.collectAsState() ?: remember {
        mutableStateOf("")
    }

    val passport by backStackEntry
        ?.savedStateHandle
        ?.getStateFlow("passport", "")
        ?.collectAsState() ?: remember {
        mutableStateOf("")
    }

    val email by backStackEntry
        ?.savedStateHandle
        ?.getStateFlow("email", "")
        ?.collectAsState() ?: remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            PersonalInfoTopBar(
                title = "Thông tin cá nhân",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Chúng tôi sẽ lưu thông tin này để giúp bạn đặt nhanh hơn.",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF444444)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PersonalInfoRow(
                        title = "Tên",
                        value = name,
                        valueColor = if (name.isBlank()) TextGray else Color.Black,
                        onClick = {
                            navController.navigate(
                                "edit_profile_field/name/${encodeNavValue(name)}"
                            )
                        }
                    )

                    HorizontalDivider(color = Color(0xFFE5E5E5))

                    PersonalInfoRow(
                        title = "Giới tính",
                        value = gender,
                        valueColor = if (gender.isBlank()) TextGray else Color.Black,
                        onClick = {
                            navController.navigate(
                                "edit_profile_field/gender/${encodeNavValue(gender)}"
                            )
                        }
                    )

                    HorizontalDivider(color = Color(0xFFE5E5E5))

                    PersonalInfoRow(
                        title = "Ngày sinh",
                        value = birthDate,
                        valueColor = if (birthDate.isBlank()) TextGray else Color.Black,
                        onClick = {
                            navController.navigate(
                                "edit_profile_field/birthdate/${encodeNavValue(birthDate)}"
                            )
                        }
                    )

                    HorizontalDivider(color = Color(0xFFE5E5E5))

                    PersonalInfoRow(
                        title = "Thông tin hộ chiếu",
                        value = passport,
                        valueColor = if (passport.isBlank()) TextGray else Color.Black,
                        onClick = {
                            navController.navigate(
                                "edit_profile_field/passport/${encodeNavValue(passport)}"
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Thông tin liên lạc",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Chỗ nghỉ hoặc nhà cung cấp dịch vụ mà bạn đặt sẽ sử dụng thông tin này nếu họ cần liên hệ bạn.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF555555)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ContactInfoRow(
                        title = "Địa chỉ email",
                        value = email,
                        badgeText = "Xác thực",
                        description = "Đây là địa chỉ email bạn dùng để đăng nhập. Chúng tôi cũng sẽ gửi các xác nhận đặt chỗ tới địa chỉ này.",
                        onClick = {
                            navController.navigate(
                                "edit_profile_field/email/${encodeNavValue(email)}"
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            text = "Bạn không truy cập được email? Nếu đã thêm số điện thoại di động cho đợt lưu trú đã hoàn tất trước đây, bạn có thể tiến hành xác thực số điện thoại di động để đổi địa chỉ email.",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF444444)
                        )
                    }
                }
            }
        }
    }
}

private fun encodeNavValue(value: String): String {
    return Uri.encode(if (value.isBlank()) "empty" else value)
}

@Composable
private fun PersonalInfoTopBar(
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

@Composable
private fun PersonalInfoRow(
    title: String,
    value: String,
    valueColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (value.isNotBlank()) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = valueColor
            )
        }
    }
}

@Composable
private fun ContactInfoRow(
    title: String,
    value: String,
    badgeText: String,
    description: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Black
            )
        }

        if (value.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF16A34A),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = badgeText,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
        } else {
            Spacer(modifier = Modifier.height(10.dp))
        }

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF555555)
        )
    }
}