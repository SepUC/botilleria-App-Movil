package com.example.botilleriaapp.repository

import com.example.botilleriaapp.model.Producto
import com.example.botilleriaapp.model.CarritoDao
import kotlinx.coroutines.flow.Flow

class CarritoRepository(private val dao: CarritoDao) {
    fun getAll(): Flow<List<Producto>> = dao.getAll()
    suspend fun insert(producto: Producto) = dao.insert(producto)
    suspend fun update(producto: Producto) = dao.update(producto)
    suspend fun delete(producto: Producto) = dao.delete(producto)
    suspend fun deleteAll() = dao.deleteAll()
}
