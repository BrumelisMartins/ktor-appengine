package com.example.demo.repository

import com.example.demo.tables.ActiveUsers
import com.example.demo.tables.Tasks
import com.example.demo.tables.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

const val DB_NAME = "postgres"
const val DB_USER = "postgres"
const val DB_PASS = "adh125DFkeO0r2Rgr"
const val CLOUD_SQL_CONNECTION_NAME = "bot-api-323113:europe-west3:bot-service"

object DatabaseFactory {

    fun init() {
        Database.connect(hikari()) // 1
        // 2
        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.create(Tasks)
            SchemaUtils.create(ActiveUsers)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql:///${DB_NAME}"
        config.username = DB_USER
        config.password = DB_PASS
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", CLOUD_SQL_CONNECTION_NAME);


        config.validate()
        return HikariDataSource(config)
    }

    // 5
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}
