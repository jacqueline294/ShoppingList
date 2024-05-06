package com.example.shoppingl_list_app.ui.theme

sealed class Route(val route: String) {
    object Home : Route("home")
    object Detail : Route("detail")

    // Add more routes here if needed

    companion object {
        fun fromString(string: String): Route {
            return when (string) {
                Home.route -> Home
                Detail.route -> Detail
                // Add more routes here if needed
                else -> throw IllegalArgumentException("Route not found for $string")
            }
        }
    }
}

