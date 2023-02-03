package com.example.petsafe2.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.petsafe2.model.Board
import com.example.petsafe2.model.Comment
import com.example.petsafe2.model.User
import com.example.petsafe2.model.local.dao.BoardDao
import com.example.petsafe2.model.local.dao.CommentDao
import com.example.petsafe2.model.local.dao.UserDao

@Database(entities = [User::class, Board::class, Comment::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {

    //Dao 추상 메소드 정의
    abstract fun getUserDao(): UserDao
    abstract fun getBoardDao(): BoardDao
    abstract fun getCommentDao(): CommentDao

    companion object{
        private var instance: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase?{
            if(instance == null){
                synchronized(LocalDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "app.db")
                        .build()
                }
            }
            return instance
        }
    }



}