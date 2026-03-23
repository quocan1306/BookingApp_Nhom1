package com.tranphanquocan.bookingks.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout

import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.ui.components.BottomNavigationBar
import com.tranphanquocan.bookingks.ui.components.ProfileItem
import com.tranphanquocan.bookingks.ui.components.SectionTitle
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.ui.theme.AccentBlue
import com.tranphanquocan.bookingks.ui.theme.ButtonBlue
import com.tranphanquocan.bookingks.ui.theme.LightGray

data class ProfileMenuItemData(
    val title: String,
    val icon: ImageVector
)

@Composable
fun ProfileScreen(
    navController: NavController,
    onLoginClick: () -> Unit = {
        navController.navigate("login")
    },
    onLogout: () -> Unit = {}
) {
    val accountItems = listOf(
        ProfileMenuItemData("Thông tin tài khoản", Icons.Outlined.PersonOutline),
        ProfileMenuItemData("Cài đặt bảo mật", Icons.Outlined.Lock),
        ProfileMenuItemData("Người đi cùng", Icons.Outlined.People)
    )

    val paymentItems = listOf(
        ProfileMenuItemData("Phương thức thanh toán", Icons.Outlined.CreditCard),
        ProfileMenuItemData("Giao dịch", Icons.Outlined.ReceiptLong)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ProfileHeader(
                    onLoginClick = onLoginClick
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                SectionTitle(title = "Quản lý tài khoản")
            }

            items(accountItems) { item ->
                ProfileItem(
                    title = item.title,
                    icon = item.icon,
                    onClick = {
                        if (item.title == "Thông tin tài khoản") {
                            navController.navigate("personal_info")
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                SectionTitle(title = "Thông tin thanh toán")
            }

            items(paymentItems) { item ->
                ProfileItem(
                    title = item.title,
                    icon = item.icon,
                    onClick = { }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileItem(
                    title = "Đăng xuất",
                    icon = Icons.AutoMirrored.Outlined.Logout,
                    textColor = Color.Red,
                    iconColor = Color.Red,
                    showArrow = false,
                    onClick = {
                        UserState.isLoggedIn.value = false
                        UserState.userName.value = ""

                        navController.navigate("home") {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    onLoginClick: () -> Unit
) {
    val isLoggedIn = UserState.isLoggedIn.value
    val userName = UserState.userName.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ButtonBlue)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {

        if (isLoggedIn) {
            // đã đăng nhập
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Chào $userName",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )


                }
            }

        } else {
            // chưa đăng nhập
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Hồ sơ cá nhân",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Đăng nhập để quản lý tài khoản, phương thức thanh toán và giao dịch của bạn",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.95f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Đăng nhập hoặc đăng ký")
                }
            }
        }
    }
}
