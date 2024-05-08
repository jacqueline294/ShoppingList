package com.example.shoppingl_list_app.ui.theme.map

import android.location.Location

class MyLocationProvider {

    // Assuming these methods are not asynchronous, removing 'suspend' keyword
    fun getCurrentLocation(): Location {
        // For demonstration, returning an empty Location object
        return Location("")
    }

    fun getLastKnownLocation(): Location? {
        // Returning null for demonstration
        return null
    }

    companion object {
        // Assuming 'current' is meant to represent the current location
        // and should be nullable if no location is available
        var current: Location? = null
            private set
    }
}
