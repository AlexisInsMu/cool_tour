package com.example.cool_tour.ui.missions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cool_tour.databinding.ItemMisionBinding
import com.example.cool_tour.domain.model.Mision

class MissionsAdapter : ListAdapter<Mision, MissionsAdapter.MisionViewHolder>(DiffCallback()) {

    inner class MisionViewHolder(private val binding: ItemMisionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mision: Mision) {
            binding.tvTituloMision.text = mision.titulo
            binding.tvDescMision.text = mision.descripcion
            binding.ivCompletada.visibility =
                if (mision.completada) android.view.View.VISIBLE else android.view.View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisionViewHolder {
        val binding = ItemMisionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MisionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MisionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Mision>() {
        override fun areItemsTheSame(oldItem: Mision, newItem: Mision) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Mision, newItem: Mision) = oldItem == newItem
    }
}