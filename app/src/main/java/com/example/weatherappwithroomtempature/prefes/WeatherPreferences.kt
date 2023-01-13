package com.example.weatherappwithroomtempature.prefes

import android.content.Context
import android.content.SharedPreferences

class WeatherPreferences(context : Context) {
    private lateinit var preference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val tempStatus="status"
    init {
        preference = context.getSharedPreferences("Weather_preference",Context.MODE_PRIVATE)
        editor=preference.edit()
    }
    fun setTempUnitStatus(status:Boolean){
        editor.putBoolean(tempStatus,status)
        editor.commit()

    }
    fun getTemunitStatus():Boolean{
        return preference.getBoolean(tempStatus,false)
    }
}