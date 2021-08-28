package com.example.demo.models

import io.ktor.auth.Principal
import java.io.Serializable

data class User(
    val email: String,
    val displayName: String,
    val passwordHash: String,
    val status: String,
    val type: String,
    val amountOfGold: Long,
    val amountOfResources: Long
) : Serializable, Principal

