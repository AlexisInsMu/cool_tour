package com.example.cool_tour.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cool_tour.databinding.ItemRutaHistorialBinding
import com.example.cool_tour.domain.model.Ruta

class HistorialRutasAdapter : ListAdapter<Ruta, HistorialRutasAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemRutaHistorialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ruta: Ruta) {
            binding.tvNombreRuta.text = ruta.nombre
            binding.tvCantidadPois.text = "${ruta.pois.size} puntos"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRutaHistorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class DiffCallback : DiffUtil.ItemCallback<Ruta>() {
        override fun areItemsTheSame(oldItem: Ruta, newItem: Ruta) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Ruta, newItem: Ruta) = oldItem == newItem
    }
}