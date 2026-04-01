package com.tranphanquocan.bookingks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.tranphanquocan.bookingks.R
import com.tranphanquocan.bookingks.data.model.Destinations
import com.tranphanquocan.bookingks.data.model.Hotel
import com.tranphanquocan.bookingks.ui.screen.home.HomeScreen
import com.tranphanquocan.bookingks.ui.screen.hotel.HotelDetailScreen
import com.tranphanquocan.bookingks.ui.screen.hotel.HotelListScreen
import com.tranphanquocan.bookingks.ui.screen.login.LoginScreen
import com.tranphanquocan.bookingks.ui.screen.profile.ChangePasswordScreen
import com.tranphanquocan.bookingks.ui.screen.profile.EditProfileFieldScreen
import com.tranphanquocan.bookingks.ui.screen.profile.PaymentMethodScreen
import com.tranphanquocan.bookingks.ui.screen.profile.PersonalInfoScreen
import com.tranphanquocan.bookingks.ui.screen.profile.ProfileScreen
import com.tranphanquocan.bookingks.ui.screen.profile.SecuritySettingsScreen
import com.tranphanquocan.bookingks.ui.screen.profile.companion.AddEditCompanionScreen
import com.tranphanquocan.bookingks.ui.screen.profile.companion.CompanionsScreen
import com.tranphanquocan.bookingks.ui.screen.register.RegisterScreen
import com.tranphanquocan.bookingks.ui.screen.saved.SavedScreen
import com.tranphanquocan.bookingks.ui.state.UserState
import com.tranphanquocan.bookingks.viewmodel.AuthViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            UserState.isLoggedIn.value = true
            UserState.userName.value = user.displayName ?: user.email ?: ""

            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }

        } else {
            UserState.isLoggedIn.value = false
            UserState.userName.value = ""
        }
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                navController = navController,
                viewModel = authViewModel,
                onBackToHome = {
                    navController.navigate("home")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onBackToLogin = {
                    navController.popBackStack()
                },
                navController = navController,
                viewModel = authViewModel,
                onBackToHome = {
                    navController.navigate("home")
                }
            )
        }

        composable("home") {
            val hotels = listOf(
                Hotel(
                    name = "Resort Cam Ranh",
                    location = "Khánh Hòa",
                    rating = 9.2,
                    reviewCount = 120,
                    tag = "Ưu đãi mùa hè",
                    oldPrice = "VND 3.200.000",
                    newPrice = "VND 2.900.000",
                    image = R.drawable.hotelcard_hotel3
                ),
                Hotel(
                    name = "Vinpearl Resort",
                    location = "Nha Trang",
                    rating = 9.0,
                    reviewCount = 540,
                    tag = "Giảm giá 30%",
                    oldPrice = "VND 4.000.000",
                    newPrice = "VND 3.200.000",
                    image = R.drawable.hotelcard_hotel1
                ),
                Hotel(
                    name = "Vinpearl Resort",
                    location = "Nha Trang",
                    rating = 9.0,
                    reviewCount = 540,
                    tag = "Giảm giá 30%",
                    oldPrice = "VND 4.000.000",
                    newPrice = "VND 3.200.000",
                    image = R.drawable.hotelcard_hotel2
                )
            )

            val destinations = listOf(
                Destinations(
                    name = "TP. Hồ Chí Minh",
                    location = "Hồ Chí Minh",
                    image = R.drawable.destinationcard_hochiminhcity
                ),
                Destinations(
                    name = "Hà Nội",
                    location = "Hà Nội",
                    image = R.drawable.destinationcard_hanoi
                ),
                Destinations(
                    name = "Đà Nẵng",
                    location = "Đà Nẵng",
                    image = R.drawable.destinationcard_danang
                )
            )

            HomeScreen(
                hotels = hotels,
                destinations = destinations,
                navController = navController,
                viewModel = authViewModel
            )
        }

        composable("saved") {
            SavedScreen(navController = navController)
        }

        composable(
            route = "hotel_list/{location}/{checkIn}/{checkOut}",
            arguments = listOf(
                navArgument("location") { type = NavType.StringType },
                navArgument("checkIn") { type = NavType.StringType },
                navArgument("checkOut") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""
            val checkIn = backStackEntry.arguments?.getString("checkIn") ?: ""
            val checkOut = backStackEntry.arguments?.getString("checkOut") ?: ""

            val hotels = listOf(
                Hotel(
                    name = "Lucky Star Hotel - Lê Thị Riêng",
                    location = "Hồ Chí Minh",
                    rating = 7.6,
                    reviewCount = 104,
                    tag = "Ưu đãi đầu năm 2026",
                    oldPrice = "VND 12.000.000",
                    newPrice = "VND 1.560.000",
                    image = R.drawable.hotelcard_hotel3
                ),
                Hotel(
                    name = "Urban Airport Home",
                    location = "Hồ Chí Minh",
                    rating = 10.0,
                    reviewCount = 3,
                    tag = "Ưu đãi trong thời gian có hạn",
                    oldPrice = "VND 2.280.000",
                    newPrice = "VND 1.140.000",
                    image = R.drawable.hotelcard_hotel2
                ),
                Hotel(
                    name = "Vinpearl Resort",
                    location = "Nha Trang",
                    rating = 9.0,
                    reviewCount = 540,
                    tag = "Giảm giá 30%",
                    oldPrice = "VND 4.000.000",
                    newPrice = "VND 3.200.000",
                    image = R.drawable.hotelcard_hotel1
                ),
                Hotel(
                    name = "Resort Cam Ranh",
                    location = "Khánh Hòa",
                    rating = 9.2,
                    reviewCount = 120,
                    tag = "Ưu đãi mùa hè",
                    oldPrice = "VND 3.200.000",
                    newPrice = "VND 2.900.000",
                    image = R.drawable.hotelcard_hotel3
                )
            )

            val filteredHotels = hotels
                .filter { it.location.contains(location, ignoreCase = true) }
                .sortedBy { parsePrice(it.newPrice) }

            HotelListScreen(
                navController = navController,
                location = location,
                checkIn = checkIn,
                checkOut = checkOut,
                hotels = filteredHotels
            )
        }

        composable(
            route = "hotel_list_by_location/{location}",
            arguments = listOf(
                navArgument("location") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val location = backStackEntry.arguments?.getString("location") ?: ""

            val hotels = listOf(
                Hotel(
                    name = "Lucky Star Hotel - Lê Thị Riêng",
                    location = "Hồ Chí Minh",
                    rating = 7.6,
                    reviewCount = 104,
                    tag = "Ưu đãi đầu năm 2026",
                    oldPrice = "VND 12.000.000",
                    newPrice = "VND 1.560.000",
                    image = R.drawable.hotelcard_hotel3
                ),
                Hotel(
                    name = "Urban Airport Home",
                    location = "Hồ Chí Minh",
                    rating = 10.0,
                    reviewCount = 3,
                    tag = "Ưu đãi trong thời gian có hạn",
                    oldPrice = "VND 2.280.000",
                    newPrice = "VND 1.140.000",
                    image = R.drawable.hotelcard_hotel2
                ),
                Hotel(
                    name = "Vinpearl Resort",
                    location = "Hồ Chí Minh",
                    rating = 9.0,
                    reviewCount = 540,
                    tag = "Giảm giá 30%",
                    oldPrice = "VND 4.000.000",
                    newPrice = "VND 3.200.000",
                    image = R.drawable.hotelcard_hotel1
                ),
                Hotel(
                    name = "Resort Cam Ranh",
                    location = "Hồ Chí Minh",
                    rating = 9.2,
                    reviewCount = 120,
                    tag = "Ưu đãi mùa hè",
                    oldPrice = "VND 3.200.000",
                    newPrice = "VND 2.900.000",
                    image = R.drawable.hotelcard_hotel3
                ),
                Hotel(
                    name = "Lucky Star Hotel - Lê Thị Riêng",
                    location = "Hà Nội",
                    rating = 7.6,
                    reviewCount = 104,
                    tag = "Ưu đãi đầu năm 2026",
                    oldPrice = "VND 12.000.000",
                    newPrice = "VND 1.560.000",
                    image = R.drawable.hotelcard_hotel3
                ),
                Hotel(
                    name = "Urban Airport Home",
                    location = "Hà Nội",
                    rating = 10.0,
                    reviewCount = 3,
                    tag = "Ưu đãi trong thời gian có hạn",
                    oldPrice = "VND 2.280.000",
                    newPrice = "VND 1.140.000",
                    image = R.drawable.hotelcard_hotel2
                ),
                Hotel(
                    name = "Vinpearl Resort",
                    location = "Hà Nội",
                    rating = 9.0,
                    reviewCount = 540,
                    tag = "Giảm giá 30%",
                    oldPrice = "VND 4.000.000",
                    newPrice = "VND 3.200.000",
                    image = R.drawable.hotelcard_hotel1
                ),
                Hotel(
                    name = "Resort Cam Ranh",
                    location = "Hà Nội",
                    rating = 9.2,
                    reviewCount = 120,
                    tag = "Ưu đãi mùa hè",
                    oldPrice = "VND 3.200.000",
                    newPrice = "VND 2.900.000",
                    image = R.drawable.hotelcard_hotel3
                )
            )

            val filteredHotels = hotels
                .filter { it.location.contains(location, ignoreCase = true) }
                .sortedBy { parsePrice(it.newPrice) }

            HotelListScreen(
                navController = navController,
                location = location,
                checkIn = "",
                checkOut = "",
                hotels = filteredHotels
            )
        }

        composable(
            route = "hotel_detail/{hotelName}/{hotelLocation}/{checkIn}/{checkOut}/{hotelImage}/{hotelTag}/{oldPrice}/{newPrice}",
            arguments = listOf(
                navArgument("hotelName") { type = NavType.StringType },
                navArgument("hotelLocation") { type = NavType.StringType },
                navArgument("checkIn") { type = NavType.StringType },
                navArgument("checkOut") { type = NavType.StringType },
                navArgument("hotelImage") { type = NavType.IntType },
                navArgument("hotelTag") { type = NavType.StringType },
                navArgument("oldPrice") { type = NavType.StringType },
                navArgument("newPrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val hotelName = backStackEntry.arguments?.getString("hotelName") ?: ""
            val hotelLocation = backStackEntry.arguments?.getString("hotelLocation") ?: ""
            val rawCheckIn = backStackEntry.arguments?.getString("checkIn") ?: ""
            val rawCheckOut = backStackEntry.arguments?.getString("checkOut") ?: ""
            val hotelImage = backStackEntry.arguments?.getInt("hotelImage") ?: R.drawable.hotelcard_hotel1
            val hotelTag = backStackEntry.arguments?.getString("hotelTag") ?: ""
            val oldPrice = backStackEntry.arguments?.getString("oldPrice") ?: ""
            val newPrice = backStackEntry.arguments?.getString("newPrice") ?: ""

            val checkIn = if (rawCheckIn == "empty") "" else rawCheckIn
            val checkOut = if (rawCheckOut == "empty") "" else rawCheckOut

            HotelDetailScreen(
                navController = navController,
                hotelName = hotelName,
                hotelLocation = hotelLocation,
                checkIn = checkIn,
                checkOut = checkOut,
                hotelImage = hotelImage,
                hotelTag = hotelTag,
                oldPrice = oldPrice,
                newPrice = newPrice
            )
        }

        composable("profile") {
            ProfileScreen(
                navController = navController,
                onLogout = {
                    authViewModel.logout()

                    UserState.isLoggedIn.value = false
                    UserState.userName.value = ""

                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }

        composable("personal_info") {
            PersonalInfoScreen(navController = navController)
        }

        composable("security_settings") {
            SecuritySettingsScreen(navController = navController)
        }

        composable("change_password") {
            ChangePasswordScreen(navController = navController)
        }

        composable("companions") {
            CompanionsScreen(navController = navController)
        }

        composable("add_edit_companion") {
            AddEditCompanionScreen(
                navController = navController,
                companionId = null
            )
        }

        composable(
            route = "add_edit_companion/{companionId}",
            arguments = listOf(
                navArgument("companionId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val companionId = backStackEntry.arguments?.getInt("companionId")

            AddEditCompanionScreen(
                navController = navController,
                companionId = companionId
            )
        }

        composable(
            route = "edit_profile_field/{fieldType}/{currentValue}",
            arguments = listOf(
                navArgument("fieldType") { type = NavType.StringType },
                navArgument("currentValue") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val fieldType = backStackEntry.arguments?.getString("fieldType") ?: ""
            val currentValue = backStackEntry.arguments?.getString("currentValue") ?: ""

            EditProfileFieldScreen(
                navController = navController,
                fieldType = fieldType,
                currentValue = currentValue
            )
        }

        composable("payment_method") {
            PaymentMethodScreen(navController = navController)
        }
    }
}

private fun parsePrice(price: String): Long {
    return price
        .replace("VND", "")
        .replace(".", "")
        .replace(",", "")
        .trim()
        .toLongOrNull() ?: Long.MAX_VALUE
}