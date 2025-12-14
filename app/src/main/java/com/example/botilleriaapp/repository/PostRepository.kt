package com.example.botilleriaapp.repository

import com.example.botilleriaapp.model.Post

/**
 * Interfaz que define las operaciones para obtener datos de Posts.
 * En una app real, la implementación de esta interfaz se conectaría a una API o a una base de datos.
 */
interface PostRepository {
    suspend fun getPosts(): List<Post>
}
