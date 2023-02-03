package com.example.petsafe2.model.local.dao

import androidx.room.*
import com.example.petsafe2.model.Comment

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment WHERE bno = :bno")
    fun getComments(bno: Int): List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(vararg comment: Comment)

    @Update
    fun updateComment(vararg comment: Comment)

    @Delete
    fun commentDelete(comment: Comment)

}