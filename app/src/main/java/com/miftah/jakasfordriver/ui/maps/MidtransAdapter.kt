package com.miftah.jakasfordriver.ui.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miftah.jakasfordriver.databinding.ItemTransactionBinding
import com.miftah.jakasfordriver.utils.Constants
import com.miftah.jakasfordriver.utils.Passenger

class MidtransAdapter : ListAdapter<Passenger, MidtransAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(val binding : ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(passenger : Passenger) {
            binding.tvPassengerFare.text = Constants.formatToRupiah(passenger.fare)
            binding.tvPassengerName.text = passenger.passengerName
            binding.tvPassengerStatus.text = if(passenger.onProgress) "Finish" else "On Progress"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Passenger>() {
            override fun areItemsTheSame(
                oldItem: Passenger,
                newItem: Passenger
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Passenger,
                newItem: Passenger
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}