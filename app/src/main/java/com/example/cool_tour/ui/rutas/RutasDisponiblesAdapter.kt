package com.example.cool_tour.ui.rutas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cool_tour.databinding.ItemRutaDisponibleBinding
import com.example.cool_tour.domain.model.RutaResumen
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class RutasDisponiblesAdapter(
    private val onRutaClick: (RutaResumen) -> Unit
) : ListAdapter<RutaResumen, RutasDisponiblesAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemRutaDisponibleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ruta: RutaResumen) {
            binding.tvNombreRutaDisponible.text = ruta.nombre
            binding.tvResumenRuta.text = ruta.resumenCorto
            binding.chipTiempoEstimado.text = "⏱ ${ruta.tiempoEstimadoMin} min"
            binding.chipCantidadParadas.text = "📍 ${ruta.cantidadPois} paradas"
            binding.chipDificultad.text = ruta.dificultad.replaceFirstChar { it.uppercase() }

            // ← Cargar imagen desde URL
            if (!ruta.imagenPortada.isNullOrBlank()) {
                Glide.with(binding.root.context)
                    .load(ruta.imagenPortada)
                    .centerCrop()
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivPortadaRuta)
            } else {
                binding.ivPortadaRuta.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            binding.root.setOnClickListener { onRutaClick(ruta) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRutaDisponibleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class DiffCallback : DiffUtil.ItemCallback<RutaResumen>() {
        override fun areItemsTheSame(oldItem: RutaResumen, newItem: RutaResumen) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: RutaResumen, newItem: RutaResumen) = oldItem == newItem
    }
}