package com.tranphanquocan.bookingks.ui.screen.hotel

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Bathtub
import androidx.compose.material.icons.outlined.LocalParking
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranphanquocan.bookingks.R
import com.tranphanquocan.bookingks.data.model.SavedHotelItem
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.ui.theme.PrimaryBlue

@Composable
fun HotelDetailScreen(
    navController: NavController,
    hotelName: String,
    hotelLocation: String,
    checkIn: String = "",
    checkOut: String = "",
    guestInfo: String = "1 phòng, 2 người lớn, 0 trẻ em",
    hotelImage: Int,
    hotelTag: String,
    oldPrice: String,
    newPrice: String
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val displayCheckIn = if (checkIn.isBlank()) "Chưa chọn ngày" else checkIn
    val displayCheckOut = if (checkOut.isBlank()) "Chưa chọn ngày" else checkOut

    val isSaved = UserState.isHotelSaved(
        name = hotelName,
        location = hotelLocation,
        checkIn = checkIn,
        checkOut = checkOut
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F5F5),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            HotelDetailTopBar(
                title = hotelName,
                isSaved = isSaved,
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = {
                    UserState.toggleSavedHotel(
                        SavedHotelItem(
                            name = hotelName,
                            location = hotelLocation,
                            tag = hotelTag,
                            oldPrice = oldPrice,
                            newPrice = newPrice,
                            image = hotelImage,
                            checkIn = checkIn,
                            checkOut = checkOut
                        )
                    )
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Chọn phòng",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(top = innerPadding.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                HotelHeaderSection(
                    hotelName = hotelName,
                    hotelLocation = hotelLocation,
                    checkIn = displayCheckIn,
                    checkOut = displayCheckOut,
                    guestInfo = guestInfo
                )
            }

            item {
                AttractionSection(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }

            item {
                PolicySection()
            }

            item {
                OverviewSection()
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun HotelDetailTopBar(
    title: String,
    isSaved: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
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
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = if (isSaved) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isSaved) Color.Red else Color.White,
            modifier = Modifier.clickable { onFavoriteClick() }
        )
    }
}

@Composable
private fun HotelHeaderSection(
    hotelName: String,
    hotelLocation: String,
    checkIn: String,
    checkOut: String,
    guestInfo: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = hotelName,
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "$hotelLocation • Địa điểm rất tốt!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF222222)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = PrimaryBlue,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "7,5",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        PhotoGridSection()
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FeatureCircleItem(
                icon = Icons.Outlined.LocalParking,
                label = "Chỗ đỗ xe"
            )
            FeatureCircleItem(
                icon = Icons.Default.Info,
                label = "Điều hòa không khí"
            )
            FeatureCircleItem(
                icon = Icons.Outlined.Bathtub,
                label = "Phòng tắm riêng"
            )
            FeatureCircleItem(
                icon = Icons.Outlined.Wifi,
                label = "WiFi miễn phí"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Nhận phòng",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = checkIn,
                    style = MaterialTheme.typography.headlineSmall,
                    color = PrimaryBlue
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Trả phòng",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = checkOut,
                    style = MaterialTheme.typography.headlineSmall,
                    color = PrimaryBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Số lượng phòng và khách",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = guestInfo,
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryBlue
        )
    }
}

@Composable
private fun PhotoGridSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.hotelcard_hotel1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hotelcard_hotel2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hotelcard_hotel3),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(6.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.destinationcard_hochiminhcity),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(
                                color = Color.Black.copy(alpha = 0.45f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "+47",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureCircleItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(76.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color(0xFFEDEDED), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

@Composable
private fun AttractionSection(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            text = "149/14A Le Thi Rieng Street, Ward 1, Quận 1, TP. Hồ Chí Minh, Việt Nam • 1,0 km từ trung tâm • Địa điểm rất tốt!",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF222222),
            modifier = Modifier.padding(16.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Địa điểm tham quan hàng đầu",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            NearbyItem("Bảo tàng chứng tích chiến tranh", "16 phút (1,4 km)")
            NearbyItem("Bảo tàng lịch sử Việt Nam", "2 phút (3,1 km)")
            NearbyItem("Bến cảng Nhà Rồng", "3 phút (3,4 km)")

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Xung quanh có gì?",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            NearbyItem("Công Viên Tao Đàn", "4 phút (350 m)")
            NearbyItem("Me Va Be", "6 phút (500 m)")
            NearbyItem("Zombie Team", "6 phút (550 m)")

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Thời gian đi bộ và lái xe được dựa theo tuyến đường nhanh nhất tính từ chỗ nghỉ. Khi không có tuyến đường nào, khoảng cách hiển thị được tính theo đường thẳng. Thời gian và khoảng cách di chuyển thực tế có thể sẽ khác.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun NearbyItem(
    name: String,
    time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = time,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
private fun PolicySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Chính sách",
            style = MaterialTheme.typography.displaySmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Nhận phòng: từ 14:00 đến 00:00",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Text(
            text = "Trả phòng: đến 12:00",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF16A34A),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Miễn phí",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Wi-fi có ở các phòng khách sạn và miễn phí.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Không phí đặt phòng hoặc phí thẻ tín dụng",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Xem chính sách chỗ nghỉ",
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryBlue
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalDivider(color = Color(0xFFE5E5E5))

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Trẻ em và giường phụ",
            style = MaterialTheme.typography.displaySmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Phù hợp cho tất cả trẻ em.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Trẻ em từ 7 tuổi trở lên sẽ được tính giá như người lớn tại chỗ nghỉ này.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Để xem thông tin giá và tình trạng phòng trống chính xác, vui lòng thêm số lượng và độ tuổi của trẻ em trong nhóm của bạn khi tìm kiếm.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Xem toàn bộ chính sách",
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryBlue
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalDivider(color = Color(0xFFE5E5E5))

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Chương trình Đối tác Ưu tiên",
            style = MaterialTheme.typography.displaySmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Chỗ nghỉ này nằm trong Chương trình Ưu tiên Đặc biệt của chúng tôi. Nơi này cam kết mang lại cho khách dịch vụ xuất sắc với giá trị tuyệt vời.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
private fun OverviewSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Những tiện nghi chỗ nghỉ này cung cấp",
            style = MaterialTheme.typography.displaySmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Nhận phòng từ 14:00 đến 00:00; trả phòng chậm nhất vào 12:00.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Nằm tại vị trí thuận tiện ở Quận 1, TP. Hồ Chí Minh, Lucky Star Hotel – Lê Thị Riêng tọa lạc cách Công viên Tao Đàn 13 phút đi bộ, Bảo tàng Mỹ thuật 1.6 km và Bảo tàng chứng tích chiến tranh ...",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Đọc thêm",
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryBlue
        )
    }
}