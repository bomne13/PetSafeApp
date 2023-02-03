package com.example.petsafe2.model

import android.net.Uri
import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "member")
data class User(
    @PrimaryKey @ColumnInfo(name = "id")@SerializedName("id") val userId: String,
    val pwd: String,
    val name: String? = null,
    val email: String? = null,
    val nickname: String? = null,
    val regDate: String? = null,
    val profileImage: String? = ""
) : Parcelable


@Parcelize
data class UserAccess(
    val idToken: String,
    val email: String,
    val pwd: String?,
    val name: String,
    val nickname: String?,
    val profile: Uri?
) : Parcelable
