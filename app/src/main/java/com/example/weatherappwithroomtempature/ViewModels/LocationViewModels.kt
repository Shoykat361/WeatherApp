package com.example.weatherappwithroomtempature.ViewModels

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappwithroomtempature.models.CurrentModel
import com.example.weatherappwithroomtempature.models.ForecastData
import com.example.weatherappwithroomtempature.repos.WeatherRepository
import kotlinx.coroutines.launch

class LocationViewModels :ViewModel(){
    val repository = WeatherRepository()
    val LocationLIvedata : MutableLiveData<Location> = MutableLiveData()
    val currentModelLD :MutableLiveData<CurrentModel> = MutableLiveData()
    val forcastModelLD : MutableLiveData<ForecastData> = MutableLiveData()
    fun setNewLocation(location: Location){
        LocationLIvedata.value=location
    }
    fun fetchData(status : Boolean= false){
        viewModelScope.launch {
            try {
                currentModelLD.value=repository.fetchCurrentWeatherData(LocationLIvedata.value!!,status=status)
                forcastModelLD.value=repository.fetchForcastWeatherData(LocationLIvedata.value!!,status=status)

            }catch (e :Exception){
                Log.d("locationViewModel", e.localizedMessage)

            }
        }

    }
}