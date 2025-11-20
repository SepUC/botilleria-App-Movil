package com.example.botilleriaapp.data.repository

import com.example.botilleriaapp.data.model.Producto
import com.example.botilleriaapp.data.remote.RetrofitInstance

class ProductoRepository {
    private val apiService = RetrofitInstance.api

    suspend fun getProductos(): List<Producto> {
        return apiService.getProductos()
    }
}