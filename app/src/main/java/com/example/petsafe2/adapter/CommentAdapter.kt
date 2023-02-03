package com.example.petsafe2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petsafe2.view.MainViewModel
import com.example.petsafe2.databinding.ListItemCommentBinding
import com.example.petsafe2.model.Comment

class CommentAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    private var commentList = emptyList<Comment>()

    inner class CommentHolder(private val binding: ListItemCommentBinding): RecyclerView.ViewHolder(binding.root){
        lateinit var comment: Comment
        lateinit var mainViewModel: MainViewModel

        fun bind(currentcomment: Comment, mainViewModel: MainViewModel){
            binding.comment = currentcomment
            this.mainViewModel = mainViewModel
        }


    }

    //viewHolder로 생성할 xml 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding = ListItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(commentList[position], mainViewModel)
    }

    //viewHolder 개수 반환
    override fun getItemCount(): Int {
        return commentList.size
    }
}