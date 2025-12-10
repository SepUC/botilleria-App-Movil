package com.example.botilleriaapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Query("SELECT * FROM productos")
    fun getAll(): Flow<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)

    @Query("DELETE FROM productos")
    suspend fun deleteAll()
}
