package com.example.petsafe2.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
//unique속성 사용해 제약 조건 표기
@Entity(tableName = "comment", inheritSuperIndices=true,
    foreignKeys = [
        ForeignKey(
            entity = Board::class,
            parentColumns = ["bno"],
            childColumns = ["bno"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Comment(
    @PrimaryKey(autoGenerate= true) @ColumnInfo(name = "rno") val rno: Int,
    @ColumnInfo(name="bno") val bno: Int,
    val content: String,
    val userId: String,
    val regDate: String,
    val likeCnt: Int
): Parcelable
