package com.example.petsafe2.model.repository

import android.util.Log
import com.example.petsafe2.model.network.api.BoardListResult
import com.example.petsafe2.model.network.api.BoardService
import com.example.petsafe2.model.Board
import com.example.petsafe2.model.local.dao.BoardDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class BoardRepository(private val boardDao: BoardDao, private val boardService: BoardService) {

    val getBoards: Flow<List<Board>> = boardDao.getBoards()

    fun getBoardList(): Call<BoardListResult>{
        return boardService.getBoardList()
    }

    suspend fun insertBoard(board: Board){
        boardDao.insertBoard(board)
        Log.d("로그 :", "getBoard: 게시글 추가 실행")
    }

    suspend fun updateBoard(board: Board){
        boardDao.updateBoard(board)
    }

    suspend fun boardDelete(board: Board){
        boardDao.boardDelete(board)
    }

    suspend fun delete(){
        boardDao.delete()
    }


}