package com.example.botilleriaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botilleriaapp.model.Producto
import com.example.botilleriaapp.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {

    val nombre = MutableStateFlow("")
    val precio = MutableStateFlow(0)

    val carrito = MutableStateFlow<List<Producto>>(emptyList())


    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            carrito.value = repository.getAll()
        }
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.insert(producto)
            cargarProductos()
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.update(producto)
            cargarProductos()
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.delete(producto)
            cargarProductos()
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.deleteAll()
            cargarProductos()
        }
    }
}