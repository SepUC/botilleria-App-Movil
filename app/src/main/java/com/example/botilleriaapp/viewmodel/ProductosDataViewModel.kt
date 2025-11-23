package com.example.botilleriaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botilleriaapp.data.model.Producto
import com.example.botilleriaapp.repository.ProductosDataRepositoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductosDataViewModel : ViewModel() {
    private val repository = ProductosDataRepositoryRepository()
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productosData: StateFlow<List<Producto>> = _productos

    init {
        fetchProductos()
    }

    private fun fetchProductos() {
        viewModelScope.launch {
            _productos.value = repository.getProductos()
        }
    }
}