package com.example.myapplication

import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val name: String,
    val phoneNumber: String,
    val position: String,
    val email: String,
    val address: String,
    val birthDate: String
)