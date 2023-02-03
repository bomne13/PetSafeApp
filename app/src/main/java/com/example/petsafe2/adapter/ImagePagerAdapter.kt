package com.example.petsafe2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.petsafe2.R
import com.example.petsafe2.databinding.ListItemImagePagerBinding

class ImagePagerAdapter(var members: java.util.ArrayList<Int>): RecyclerView.Adapter<ImagePagerAdapter.ImagePagerHolder>() {

    companion object{
        const val TAG = "PetSafe2:: ImagePagerAdapter"
    }

    class ImagePagerHolder(private val binding: ListItemImagePagerBinding) : RecyclerView.ViewHolder(binding.root) {
        val members = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePagerHolder {
        val binding = ListItemImagePagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagePagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagePagerHolder, position: Int) {
        holder.members.setImageResource(members[position])
    }

    override fun getItemCount(): Int{
        return members.size
    }
}