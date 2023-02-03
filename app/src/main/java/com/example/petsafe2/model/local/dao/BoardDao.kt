package com.example.petsafe2.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.petsafe2.model.Board
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardDao {

    @Query("SELECT * FROM board ORDER BY BNO DESC")
    fun getBoards(): Flow<List<Board>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBoard(vararg board: Board)

    @Update
    fun updateBoard(vararg board: Board)

    @Query("DELETE FROM board")
    fun delete()

    @Delete
    fun boardDelete(board: Board)

}