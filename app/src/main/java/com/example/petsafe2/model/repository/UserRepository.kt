package com.example.petsafe2.model.repository


import com.example.petsafe2.model.network.api.UserService
import com.example.petsafe2.model.User
import com.example.petsafe2.model.local.dao.UserDao
import com.example.petsafe2.model.network.api.UserRetrofitBody
import com.example.petsafe2.model.network.api.UserRetrofitResult
import retrofit2.Call

class UserRepository(private val userDao: UserDao, private val userService: UserService) {

    fun getLoginCheck(userId: String, userPwd: String): Call<UserRetrofitResult> {
        return userService.getLoginCheck(UserRetrofitBody(userId, userPwd))
    }

    suspend fun getUser(userId: String, userPwd: String): User {
        return userDao.getUser(userId, userPwd)
    }

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun delete(user: User){
        userDao.delete(user)
    }

    suspend fun deleteUserTable(){
        userDao.deleteUserTable()
    }


}