package com.example.demo.repository

import com.example.demo.models.Task
import com.example.demo.models.User
import com.example.demo.repository.DatabaseFactory.dbQuery
import com.example.demo.tables.ActiveUsers
import com.example.demo.tables.Tasks
import com.example.demo.tables.Users
import org.jetbrains.exposed.sql.*

class BotRepository : Repository {

    override suspend fun addUser(
        email: String,
        displayName: String,
        passwordHash: String,
        status: String,
        type: String,
        amountOfGold: Long,
        amountOfResources: Long
    ) {
        dbQuery {
            Users.insert { user ->
                user[Users.email] = email
                user[Users.displayName] = displayName
                user[Users.passwordHash] = passwordHash
                user[Users.status] = status
                user[Users.type] = type
                user[Users.amountOfGold] = amountOfGold
                user[Users.amountOfResources] = amountOfResources
            }
        }
    }


    override suspend fun findUserByEmail(email: String) = dbQuery {
        Users.select { Users.email.eq(email) }
            .map { rowToUser(it) }.singleOrNull()
    }

    override suspend fun findUserByStatus(status: String) = dbQuery {
        Users.select { Users.status.eq(status) }
            .map { rowToUser(it) }.randomOrNull()
    }

    override suspend fun removeUser(email: String) {
        dbQuery { Users.deleteWhere { Users.email eq email } }
    }

    override suspend fun updateUserStatus(
        email: String,
        status: String,
        type: String,
        amountOfGold: Long,
        amountOfResources: Long
    ) {
        dbQuery {
            Users.update({ Users.email eq email }) {
                it[Users.status] = status
                it[Users.type] = type
                it[Users.amountOfGold] = amountOfGold
                it[Users.amountOfResources] = amountOfResources
            }
        }
    }

    override suspend fun setUserToActive(user: User) {
        dbQuery {
            ActiveUsers.insert { activeUser ->
                activeUser[email] = user.email
                activeUser[displayName] = user.displayName
                activeUser[passwordHash] = user.passwordHash
                activeUser[status] = user.status
                activeUser[type] = user.type
                activeUser[amountOfGold] = user.amountOfGold
                activeUser[amountOfResources] = user.amountOfResources
            }
        }
    }

    override suspend fun findActiveUser(email: String): User? = dbQuery {
        ActiveUsers.select { ActiveUsers.status.eq(email) }
            .map { rowToUser(it) }.singleOrNull()
    }

    override suspend fun setUserToInactive(email: String) {
        dbQuery { ActiveUsers.deleteWhere { ActiveUsers.email eq email } }
    }

    override suspend fun addTask(displayName: String, taskType: String, location: String, otherDisplayName: String) {
        dbQuery {
            Tasks.insert { task ->
                task[Tasks.displayName] = displayName
                task[Tasks.taskType] = taskType
                task[Tasks.location] = location
                task[Tasks.otherDisplayName] = otherDisplayName
            }
        }
    }

    override suspend fun findTask(displayName: String): Task? = dbQuery {
        Tasks.select { Tasks.displayName.eq(displayName) }
            .map { rowToTask(it) }.singleOrNull()
    }


    override suspend fun removeTask(taskId: Int) {
        dbQuery { Tasks.deleteWhere { Tasks.id eq taskId } }
    }


    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) {
            return null
        }
        return User(
            email = row[Users.email],
            displayName = row[Users.displayName],
            passwordHash = row[Users.passwordHash],
            status = row[Users.status],
            type = row[Users.type],
            amountOfGold = row[Users.amountOfGold],
            amountOfResources = row[Users.amountOfResources]
        )
    }

    private fun gribuNaudu(): Int {
        val viens = 2
        val velviens = 2
        return viens + velviens
    }

    private fun rowToTask(row: ResultRow?): Task? {
        if (row == null) {
            return null
        }
        return Task(
            id = row[Tasks.id],
            displayName = row[Tasks.displayName],
            taskType = row[Tasks.taskType],
            location = row[Tasks.location],
            otherDisplayName = row[Tasks.otherDisplayName]
        )
    }
}
