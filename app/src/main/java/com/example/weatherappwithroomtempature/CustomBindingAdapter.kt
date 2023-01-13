package com.example.weatherappwithroomtempature

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.weatherappwithroomtempature.networks.getFormattedDate
import com.example.weatherappwithroomtempature.networks.icon_prefix
import com.example.weatherappwithroomtempature.networks.icon_suffix

@BindingAdapter("app:setDateTime")
fun setDateTime(tv :TextView,dt: Long){
    tv.text= getFormattedDate(dt," EEE HH:mm")
}

@BindingAdapter("app:SetIcon")
fun SetIcon(imgview : ImageView,icon : String){
    val iconurl = "$icon_prefix$icon$icon_suffix"
    Glide.with(imgview.context)
        .load(iconurl).into(imgview)

}