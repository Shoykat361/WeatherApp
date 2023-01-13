package com.example.weatherappwithroomtempature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappwithroomtempature.databinding.ForecastItemRowBinding
import com.example.weatherappwithroomtempature.models.ForecastData

class ForecastAdapter:ListAdapter<ForecastData.
ForcastList,ForecastAdapter.ForecastViewHolder>(ForecastDiffutil()) {
    class ForecastViewHolder(val binding : ForecastItemRowBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(item: ForecastData.ForcastList){
           binding.forecast=item
       }
    }
    class ForecastDiffutil :DiffUtil.ItemCallback<ForecastData.ForcastList>() {
        override fun areItemsTheSame(
            oldItem: ForecastData.ForcastList,
            newItem: ForecastData.ForcastList
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ForecastData.ForcastList,
            newItem: ForecastData.ForcastList
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastItemRowBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}