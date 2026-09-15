package dev.faizanyousaf.jobtracker.navigation

sealed class Screen(val route : String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ResetPassword : Screen("resetPassword")
    object HomeScreen : Screen("homeScreen")
}