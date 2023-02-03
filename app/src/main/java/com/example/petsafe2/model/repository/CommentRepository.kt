package com.example.petsafe2.model.repository

import com.example.petsafe2.model.Comment
import com.example.petsafe2.model.local.dao.CommentDao

class CommentRepository(private val commentDao: CommentDao){

    suspend fun getComments(bno: Int) {
        commentDao.getComments(bno)
    }

    suspend fun insertComment(comment: Comment){
        commentDao.insertComment(comment)
    }

    suspend fun updateComment(comment: Comment){
        commentDao.updateComment(comment)
    }

    suspend fun commentDelete(comment: Comment){
        commentDao.commentDelete(comment)
    }
}