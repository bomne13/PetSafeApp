package com.example.petsafe2.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.petsafe2.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM member WHERE id = :userId AND pwd = :userPwd")
    fun getUser(userId: String, userPwd: String): User

    //OnConflictStrategy.REPLACE를 사용하면 같은 값이 들어오면 이전 데이터를 교체함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg users: User)

    @Update
    fun updateUser(vararg users: User)

    @Delete
    fun delete(users: User)

    @Query("DELETE FROM member")
    fun deleteUserTable()

}





