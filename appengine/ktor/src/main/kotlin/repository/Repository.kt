package com.example.demo.repository

import com.example.demo.models.Task
import com.example.demo.models.User

interface Repository {
    suspend fun addUser(
        email: String,
        displayName: String,
        password: String,
        status: String,
        type: String,
        amountOfGold: Long,
        amountOfResources: Long
    )

    suspend fun findUserByEmail(email: String): User?
    suspend fun findUserByStatus(status: String): User?
    suspend fun removeUser(email: String)
    suspend fun updateUserStatus(
        email: String, status: String,
        type: String,
        amountOfGold: Long,
        amountOfResources: Long
    )

    suspend fun setUserToActive(user: User)

    suspend fun setUserToInactive(email: String)

    suspend fun addTask(displayName: String, taskType: String, location: String, otherDisplayName: String)
    suspend fun findTask(displayName: String): Task?
    suspend fun removeTask(taskId: Int)


}
