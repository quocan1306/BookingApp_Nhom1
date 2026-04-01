package com.tranphanquocan.bookingks.ui.screen.profile.companion

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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
fun AddEditCompanionScreen(
    navController: NavController,
    companionId: Int?
) {
    val editingCompanion = companionId?.let { UserState.getCompanionById(it) }
    val isEditMode = editingCompanion != null

    var firstName by rememberSaveable { mutableStateOf(editingCompanion?.firstName ?: "") }
    var lastName by rememberSaveable { mutableStateOf(editingCompanion?.lastName ?: "") }
    var birthDate by rememberSaveable { mutableStateOf(editingCompanion?.birthDate ?: "") }
    var gender by rememberSaveable { mutableStateOf(editingCompanion?.gender ?: "") }
    var isConsentChecked by rememberSaveable {
        mutableStateOf(editingCompanion?.isConsentChecked ?: false)
    }

    var errorMessage by rememberSaveable { mutableStateOf("") }
    var genderExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AddEditCompanionTopBar(
                title = if (isEditMode) "Chỉnh sửa người đi cùng" else "Thêm người đi cùng",
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
                            firstName.isBlank() -> {
                                errorMessage = "Vui lòng nhập tên"
                            }
                            lastName.isBlank() -> {
                                errorMessage = "Vui lòng nhập họ"
                            }
                            birthDate.isBlank() -> {
                                errorMessage = "Vui lòng nhập ngày sinh"
                            }
                            !isConsentChecked -> {
                                errorMessage = "Bạn cần xác nhận trước khi lưu"
                            }
                            else -> {
                                errorMessage = ""

                                if (isEditMode) {
                                    UserState.updateCompanion(
                                        id = editingCompanion.id,
                                        firstName = firstName.trim(),
                                        lastName = lastName.trim(),
                                        birthDate = birthDate.trim(),
                                        gender = gender.trim(),
                                        isConsentChecked = isConsentChecked
                                    )
                                } else {
                                    UserState.addCompanion(
                                        firstName = firstName.trim(),
                                        lastName = lastName.trim(),
                                        birthDate = birthDate.trim(),
                                        gender = gender.trim(),
                                        isConsentChecked = isConsentChecked
                                    )
                                }

                                navController.popBackStack()
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
                        text = if (isEditMode) "Lưu thay đổi" else "Thêm người đi cùng",
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
                .background(LightGray)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Vui lòng hỏi ý những người đi cùng trước khi nhập thông tin cá nhân của họ.",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(24.dp))

            FieldLabel("Tên", required = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel("Họ", required = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            InfoMessage(
                text = "Vui lòng nhập chính xác tên của người này như trên hộ chiếu hoặc giấy thông hành chính thức khác của họ"
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel("Ngày sinh", required = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = birthDate,
                onValueChange = {
                    birthDate = it
                    errorMessage = ""
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("DD/MM/YYYY") },
                trailingIcon = {
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            InfoMessage(
                text = "Bạn cần phải nhập ngày sinh chính xác vì thông tin này có thể được sử dụng để đặt chỗ hoặc đặt vé"
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel("Giới tính", required = false)
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedTextField(
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { genderExpanded = true },
                    singleLine = true,
                    placeholder = { Text("Chọn giới tính") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { genderExpanded = true }
                        )
                    }
                )

                DropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Nam") },
                        onClick = {
                            gender = "Nam"
                            genderExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Nữ") },
                        onClick = {
                            gender = "Nữ"
                            genderExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            InfoMessage(
                text = "Vui lòng chọn giới tính"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = isConsentChecked,
                    onCheckedChange = {
                        isConsentChecked = it
                        errorMessage = ""
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Tôi xác nhận rằng tôi được phép cung cấp dữ liệu cá nhân của tất cả du khách đi cùng (bao gồm trẻ em) liên quan đến dịch vụ này cho Booking.com. Ngoài ra, tôi xác nhận rằng tôi đã thông báo cho họ.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF333333),
                    modifier = Modifier.weight(1f)
                )
            }

            if (errorMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (isEditMode) {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = Color(0xFFE5E5E5))
                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        UserState.deleteCompanion(editingCompanion.id)
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "Xóa người đi cùng",
                        color = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun AddEditCompanionTopBar(
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
private fun FieldLabel(
    text: String,
    required: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        if (required) {
            Text(
                text = " *",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Red
            )
        }
    }
}

@Composable
private fun InfoMessage(
    text: String
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )
    }
}