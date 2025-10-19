package com.example.botilleriaapp.model

import androidx.room.*

@Dao
interface CarritoDao {
    @Query("SELECT * FROM carrito")
    suspend fun getAll(): List<Producto>

    @Insert
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)
}