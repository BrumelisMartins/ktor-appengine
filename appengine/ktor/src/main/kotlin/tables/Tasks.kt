package com.example.demo.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Tasks : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val displayName = varchar("display_name", 64)
    val taskType = varchar("task_type", 128)
    val location = varchar("location", 128)
    val otherDisplayName = varchar("other_display_name", 64)
}
