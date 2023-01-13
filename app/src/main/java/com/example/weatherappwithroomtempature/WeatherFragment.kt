package com.example.weatherappwithroomtempature

import android.location.Geocoder
import android.location.Location
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherappwithroomtempature.ViewModels.LocationViewModels
import com.example.weatherappwithroomtempature.databinding.FragmentWeatherBinding
import com.example.weatherappwithroomtempature.models.CurrentModel
import com.example.weatherappwithroomtempature.networks.detactUserLocaton
import com.example.weatherappwithroomtempature.networks.getFormattedDate
import com.example.weatherappwithroomtempature.networks.icon_prefix
import com.example.weatherappwithroomtempature.networks.icon_suffix
import com.example.weatherappwithroomtempature.prefes.WeatherPreferences
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {
    private val TAG = "WeatherFragment"
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var preferences: WeatherPreferences
    private val locationViewModels :LocationViewModels by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_resourse_menu,menu)
        val searchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.queryHint="Search Location "
        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    convertCitytoLatLong(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.item_location){
            detactUserLocaton(requireContext()){
                locationViewModels.setNewLocation(it)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun convertCitytoLatLong(query: String) {
        val geocoder =Geocoder(requireActivity())
        val addressList = geocoder.getFromLocationName(query,1)
        if(addressList.isNotEmpty()){
            val lat = addressList[0].latitude
            val lng = addressList[0].longitude
            val location =Location("").apply {
                latitude=lat
                longitude=lng
            }
            locationViewModels.setNewLocation(location)
        }else{
            Toast.makeText(requireActivity(), "Invalid Name ", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = WeatherPreferences(requireContext())
        binding= FragmentWeatherBinding.inflate(inflater,container,false)
        binding.tempswitch.isChecked=preferences.getTemunitStatus()
        val adapter = ForecastAdapter()
        val llm = LinearLayoutManager(requireActivity())
        llm.orientation =LinearLayoutManager.HORIZONTAL
        binding.forecastRv.layoutManager=llm
        binding.forecastRv.adapter= adapter
        locationViewModels.LocationLIvedata.observe(viewLifecycleOwner){
            locationViewModels.fetchData(status = preferences.getTemunitStatus() )
        }
        locationViewModels.currentModelLD.observe(viewLifecycleOwner){
            setCurrentData(it)

        }
        locationViewModels.forcastModelLD.observe(viewLifecycleOwner){
            Log.e(TAG,"${it.list.size}")
            adapter.submitList(it.list)

        }
        // Inflate the layout for this fragment
        binding.tempswitch.setOnCheckedChangeListener { compoundButton, status ->
            preferences.setTempUnitStatus(status)
            locationViewModels.fetchData(status)
        }

        return binding.root
    }

    private fun setCurrentData(it: CurrentModel?) {
        binding.dateTv.text= getFormattedDate(it!!.dt,"MMM dd,yyyy HH:mm")
        binding.addressTv.text="${it.name},${it.sys.country}"
        val iconurl = "$icon_prefix${it.weather[0].icon}${icon_suffix}"
        Glide.with(requireActivity()).load(iconurl).into(binding.iconIv)
        binding.tempTv.text=it.main.temp.roundToInt().toString()
        binding.feelsLikeTv.text = "feels Like : ${it.main.feelsLike.roundToInt()}"
        binding.conditionTv.text = it.weather[0].description
        binding.humidityTv.text = it.main.humidity.toString()
        binding.pressureTv.text = it.main.pressure.toString()
    }
}