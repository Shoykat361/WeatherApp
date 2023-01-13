package com.example.weatherappwithroomtempature.repos

import android.location.Location
import com.example.weatherappwithroomtempature.models.CurrentModel
import com.example.weatherappwithroomtempature.models.ForecastData
import com.example.weatherappwithroomtempature.networks.NetworkService
import com.example.weatherappwithroomtempature.networks.weather_api_Key

class WeatherRepository {
    suspend fun fetchCurrentWeatherData(location: Location,status:Boolean=false):CurrentModel{
        val unit = if (status) "imperial" else "metric"
        val end_Url = "weather?lat=${location.latitude}&lon=${location.longitude}&units=$unit&appid=$weather_api_Key"
        return NetworkService.weatherServiceApi.getCurrentWeatherData(end_Url)
    }
    suspend fun fetchForcastWeatherData(location: Location,status:Boolean=false):ForecastData{
        val unit = if (status) "imperial" else "metric"
        val end_Url = "forecast?lat=${location.latitude}&lon=${location.longitude}&units=$unit&appid=$weather_api_Key"
        return NetworkService.weatherServiceApi.getForecastWeatherData(end_Url)
    }
}