package com.example.weatherappwithroomtempature

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.weatherappwithroomtempature.ViewModels.LocationViewModels
import com.example.weatherappwithroomtempature.networks.detactUserLocaton
import com.google.android.gms.location.FusedLocationProviderClient as FusedLocationProviderClient

class MainActivity : AppCompatActivity() {
    private val locationViewModels: LocationViewModels by viewModels()
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getLocation()
                // Precise location access granted.
                //Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                //Toast.makeText(this, "Coarse Denied", Toast.LENGTH_SHORT).show()
                getLocation()
            } else -> {
            // No location access granted.
        }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationPermissionRequest.launch(arrayOf(

            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
           ))
        // Manifest.permission.ACCESS_BACKGROUND_LOCATION
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        /*val provider = FusedLocationProviderClient(this)
        provider.lastLocation.addOnSuccessListener {
            it?.let {
                locationViewModels.setNewLocation(it)
            }*/
        detactUserLocaton(this){
            locationViewModels.setNewLocation(it)
        }



    }
}