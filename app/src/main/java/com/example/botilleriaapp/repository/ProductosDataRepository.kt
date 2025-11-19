package com.example.botilleriaapp.repository

import com.example.botilleriaapp.data.model.Producto
import com.example.botilleriaapp.data.remote.RetrofitInstance

class ProductosDataRepositoryRepository {
    suspend fun getProductos(): List<Producto> {
        return RetrofitInstance.api.getProductos()
    }
}