package com.maloo.paggingapplication.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maloo.paggingapplication.Utils.loadImage
import com.maloo.paggingapplication.data.model.Passenger
import com.maloo.paggingapplication.databinding.ItemPassengerBinding
import javax.inject.Inject

class PassengersAdapter :
    PagingDataAdapter<Passenger, PassengersAdapter.PassengersViewHolder>(PassengersComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PassengersViewHolder {
        return PassengersViewHolder(
            ItemPassengerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PassengersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPassenger(it) }
    }

    inner class PassengersViewHolder(private val binding: ItemPassengerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindPassenger(item: Passenger) = with(binding) {
            for (air in item.airline) {
                imageViewAirlinesLogo.loadImage(air.logo)
                textViewHeadquarters.text = air.head_quaters
                textViewNameWithTrips.text = "${item.name}, ${item.trips} Trips"
            }
        }
    }


    object PassengersComparator : DiffUtil.ItemCallback<Passenger>() {
        override fun areItemsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
            return oldItem == newItem
        }
    }
}