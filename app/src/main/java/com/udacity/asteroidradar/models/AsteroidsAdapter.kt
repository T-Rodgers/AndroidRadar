package com.udacity.asteroidradar.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemBinding

class AsteroidsAdapter(private val clickListener: AsteroidItemClickListener):
    ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(AsteroidDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener.onClick(item)
        }
        holder.bind(item)
    }

    class AsteroidViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(inflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }

    class AsteroidItemClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)

    }

    class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

    }
}


