package com.example.demo.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val email: Column<String> = varchar("email", 128).uniqueIndex().primaryKey()
    val displayName = varchar("display_name", 256)
    val passwordHash = varchar("password_hash", 64)
    val status = varchar("status", 64)
    val type = varchar("type", 64)
    val amountOfGold = long("amount_of_gold")
    val amountOfResources = long("amount_of_resources")
}
