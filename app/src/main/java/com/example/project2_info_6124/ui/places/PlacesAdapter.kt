package com.example.project2_info_6124.ui.places

//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.project2_info_6124.R
//
//class PlacesAdapter : ListAdapter<com.google.android.libraries.places.api.model.Place, PlacesAdapter.PlaceViewHolder>(PlaceDiffCallback()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_places, parent, false)
//        return PlaceViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
//        val place = getItem(position)
//        holder.bind(place)
//    }
//
//    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(place: com.google.android.libraries.places.api.model.Place) {
//            itemView.findViewById<TextView>(R.id.text_place_name).text = place.name
//            itemView.findViewById<TextView>(R.id.text_place_address).text = place.address
//        }
//    }
//}
//
//class PlaceDiffCallback : DiffUtil.ItemCallback<com.google.android.libraries.places.api.model.Place>() {
//    override fun areItemsTheSame(oldItem: com.google.android.libraries.places.api.model.Place, newItem: com.google.android.libraries.places.api.model.Place): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    @SuppressLint("DiffUtilEquals")
//    override fun areContentsTheSame(oldItem: com.google.android.libraries.places.api.model.Place, newItem: com.google.android.libraries.places.api.model.Place): Boolean {
//        return oldItem == newItem
//    }
//}

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_info_6124.R

class PlacesAdapter(private val dataList: Array<String>) :
    RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textItem: TextView = itemView.findViewById(R.id.text_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycleritem, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textItem.text = dataList[position]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}


