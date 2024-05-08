package com.example.shoppingl_list_app

import android.net.http.UrlRequest
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place


private fun <PlacesClient> PlacesClient.findAutocompletePredictions(query: String, nothing: Nothing?, searchListener: Any) {

}

open class OnPlaceSelectedListener {

}

class MapScreenActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_screen)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            googleMap = map
            // Set initial camera position
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(40.748817, -73.985428),
                    10f
                )
            )
        }
    }

    private fun initSearchFunctionality() {
        // Initialize the Google Places API client
        val placesClient = Places.createClient(this)

        // Set up a listener to handle search results
        val searchListener = object : OnPlaceSelectedListener() {
            fun onPlaceSelected(place: Place) {
                // Handle the selected place
            }

            fun onError(status: UrlRequest.Status) {
                // Handle the error
            }
        }

        // Create a search bar and set the listener
        val searchBar = findViewById<SearchView>(R.id.search_bar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform a search using the Google Places API
                placesClient.findAutocompletePredictions(query, null, searchListener)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Handle changes to the search query
                return true
            }
        })
    }
}
