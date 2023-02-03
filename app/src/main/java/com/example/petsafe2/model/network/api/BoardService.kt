package com.example.petsafe2.model.network.api

import com.example.petsafe2.model.Board
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface BoardService {
    @GET("/boardList")
    fun getBoardList(): Call<BoardListResult>
}

data class BoardListResult(
    @SerializedName("board")
    val boardList: List<Board>
)