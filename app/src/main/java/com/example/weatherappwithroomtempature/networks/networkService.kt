package com.example.weatherappwithroomtempature.networks

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.weatherappwithroomtempature.models.CurrentModel
import com.example.weatherappwithroomtempature.models.ForecastData
import com.google.android.gms.location.FusedLocationProviderClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Url
import java.text.SimpleDateFormat
import java.util.*

const val weather_api_Key = "57a1894d7aa1f47428463509e6761860"
const val base_url ="https://api.openweathermap.org/data/2.5/"
const val icon_prefix = "https://openweathermap.org/img/wn/"
const val icon_suffix="@2x.png"

fun getFormattedDate (dt : Long,pattern : String)=
    SimpleDateFormat(pattern).format(Date(dt*1000))


@SuppressLint("MissingPermission")
fun detactUserLocaton(context: Context, callback: (Location)->Unit){
        val provider = FusedLocationProviderClient(context)
        provider.lastLocation.addOnSuccessListener {
            it?.let {
                callback(it)
            }
        }

    }

val retrofit = Retrofit.Builder()
    .baseUrl(base_url)
    .addConverterFactory(GsonConverterFactory.create())
    .build()



interface WeatherServiceApi{

    @GET
    suspend fun getCurrentWeatherData(@Url endurl : String):CurrentModel

    @GET
    suspend fun getForecastWeatherData(@Url endurl : String):ForecastData

}
object NetworkService{
    val weatherServiceApi :WeatherServiceApi by lazy {
        retrofit.create(WeatherServiceApi::class.java)
    }
}
