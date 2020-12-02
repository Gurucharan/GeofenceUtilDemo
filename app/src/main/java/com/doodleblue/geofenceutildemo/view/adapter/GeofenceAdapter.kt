package com.doodleblue.geofenceutildemo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.doodleblue.geofenceutildemo.R
import com.doodleblue.geofenceutildemo.data.model.Reminder
import com.doodleblue.geofenceutildemo.databinding.RowHomeBinding

class GeofenceAdapter : RecyclerView.Adapter<GeofenceAdapter.GeofenceViewHolder>() {

    var geofences = listOf<Reminder>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GeofenceViewHolder(parent)

    override fun onBindViewHolder(holder: GeofenceViewHolder, position: Int) {
        holder.bind(geofences[position])
    }

    override fun getItemCount() = geofences.size

    class GeofenceViewHolder(
        private val parent: ViewGroup,
        private val binding: RowHomeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.row_home, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.geofence = reminder
        }

    }
}

