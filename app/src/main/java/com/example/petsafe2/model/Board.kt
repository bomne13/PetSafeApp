package com.example.petsafe2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "board", inheritSuperIndices=true,
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Board (
    @PrimaryKey(autoGenerate= true)
        var bno: Long = 0L,
        val title: String,
        val content: String,
        val userId: String,
        val regDate: String,
        val likeCnt: Int? = 0,
        val commentCnt: Int? = 0,
        val boardImage: String? = ""
): Parcelable