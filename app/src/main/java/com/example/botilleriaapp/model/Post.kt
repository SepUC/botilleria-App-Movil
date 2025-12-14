package com.example.botilleriaapp.model

/**
 * Representa un único Post. Es una clase de datos simple (POJO/POCO).
 */
data class Post(
    val id: Int,
    val title: String,
    val body: String
)
