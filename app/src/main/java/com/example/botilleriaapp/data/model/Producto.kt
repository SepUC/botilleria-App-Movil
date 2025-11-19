package com.example.botilleriaapp.data.model

import android.graphics.drawable.Drawable


data class Producto(
    val id: Int,
    val name: String,
    val price: Int,
    val cover_image: String,
    val category: String
)