package com.example.takeahike

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takeahike.HikeListAdapter.HikeViewHolder
import com.example.takeahike.model.Hike

class HikeListAdapter: ListAdapter<Hike,HikeViewHolder>(HikeComparator()) {
    class HikeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hikeNameView: TextView = itemView.findViewById(R.id.hikeName)
        private val hikeDescriptionView: TextView = itemView.findViewById(R.id.hikeDescription)
        private val hikeLocationView: TextView = itemView.findViewById(R.id.hikeLocation)
        private val hikeCategoryView: TextView = itemView.findViewById(R.id.hikeCategory)
        private val hikeDifficultyView: TextView = itemView.findViewById(R.id.hikeDifficulty)
        private val hikeDistanceView: TextView = itemView.findViewById(R.id.hikeDistance)

        fun bind(hike: Hike) {
            hikeNameView.text = hike.name
            hikeDescriptionView.text = hike.description
            hikeLocationView.text = hike.location
            hikeCategoryView.text = hike.category
            hikeDifficultyView.text = hike.difficulty
            hikeDistanceView.text = hike.distance.toString()
        }

        companion object {
            fun create(parent: ViewGroup): HikeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_hike_item, parent, false)
                return HikeViewHolder(view)
            }
        }
    }

    class HikeComparator:DiffUtil.ItemCallback<Hike>() {
        override fun areItemsTheSame(oldItem: Hike, newItem: Hike): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Hike, newItem: Hike): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HikeViewHolder {
        return HikeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HikeViewHolder, position: Int) {
        val current:Hike = getItem(position)
        holder.bind(current)
    }
}