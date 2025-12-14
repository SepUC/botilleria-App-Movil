package com.example.botilleriaapp.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Producto::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carritoDao(): CarritoDao
}