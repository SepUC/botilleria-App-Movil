package com.example.botilleriaapp.data.remote

import com.example.botilleriaapp.data.model.Producto
import retrofit2.http.GET

interface ApiService {
    @GET("api.php?endpoint=obtenerProductos")
    suspend fun getProductos(): List<Producto>
}