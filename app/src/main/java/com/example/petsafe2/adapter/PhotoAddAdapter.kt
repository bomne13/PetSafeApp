package com.example.petsafe2.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petsafe2.databinding.ListItemPhotoAddBinding
import com.example.petsafe2.view.MainViewModel

class PhotoAddAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<PhotoAddAdapter.PhotoAddHolder>() {

    companion object{
        const val TAG = "PetSafe2:: BoardAdapter"
    }

    class PhotoAddHolder(private val binding: ListItemPhotoAddBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAddHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PhotoAddHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}