package com.example.botilleriaapp.repository

import com.example.botilleriaapp.model.Producto
import com.example.botilleriaapp.model.CarritoDao

class CarritoRepository(private val dao: CarritoDao) {
    suspend fun getAll() = dao.getAll()
    suspend fun insert(producto: Producto) = dao.insert(producto)
    suspend fun update(producto: Producto) = dao.update(producto)
    suspend fun delete(producto: Producto) = dao.delete(producto)
    suspend fun deleteAll() = dao.deleteAll()
}