package com.tranphanquocan.bookingks.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tranphanquocan.bookingks.R
import com.tranphanquocan.bookingks.data.model.Destinations
import com.tranphanquocan.bookingks.data.model.Hotel
import com.tranphanquocan.bookingks.ui.screen.home.HomeScreen
import com.tranphanquocan.bookingks.ui.screen.login.LoginScreen
import com.tranphanquocan.bookingks.ui.screen.profile.PersonalInfoScreen
import com.tranphanquocan.bookingks.ui.screen.register.RegisterScreen
import com.tranphanquocan.bookingks.ui.screen.profile.ProfileScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {



        // LOGIN
        composable("login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onLoginSuccess = {
                    navController.navigate("home")
                },
                onBackToHome = {
                    navController.navigate("home")
                }
            )
        }

        // REGISTER
        composable("register") {
            RegisterScreen(
                onBackToLogin = {
                    navController.popBackStack()
                },
                onBackToHome = {
                    navController.navigate("home")
                }
            )
        }

        // HOME (gắn với HomeScreen của bạn)
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
                navController = navController
            )
        }
        // PROFILE
        composable("profile") {
            ProfileScreen(
                navController= navController,
                onLogout = {
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

    }
}