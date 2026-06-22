package com.example.cool_tour.ui.locatario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cool_tour.databinding.ItemPoiSelectionBinding
import com.example.cool_tour.domain.model.POI

class LocatarioPOIAdapter(
    private val onClick: (POI) -> Unit
) : ListAdapter<POI, LocatarioPOIAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemPoiSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(poi: POI) {
            binding.tvNombrePoi.text = poi.nombre
            // Oculta el checkbox — reutilizamos el mismo layout pero sin selección
            binding.cbSeleccionar.visibility = android.view.View.GONE
            binding.root.setOnClickListener { onClick(poi) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPoiSelectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class DiffCallback : DiffUtil.ItemCallback<POI>() {
        override fun areItemsTheSame(oldItem: POI, newItem: POI) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: POI, newItem: POI) = oldItem == newItem
    }
}