package com.example.demo.models

data class Task(
    val id: Int,
    val displayName: String,
    val taskType: String,
    val location: String,
    val otherDisplayName: String
)
