package com.example.botilleriaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botilleriaapp.model.Producto
import com.example.botilleriaapp.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {

    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito = _carrito.asStateFlow()

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        repository.getAll()
            .onEach { productos ->
                _carrito.value = productos
            }
            .launchIn(viewModelScope)
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            val productoExistente = _carrito.value.find { it.nombre == producto.nombre }

            if (productoExistente != null) {
                // Si el producto ya existe, actualiza la cantidad
                val productoActualizado = productoExistente.copy(
                    cantidad = productoExistente.cantidad + producto.cantidad
                )
                repository.update(productoActualizado)
            } else {
                // Si es un producto nuevo, lo inserta
                repository.insert(producto)
            }
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.update(producto)
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.delete(producto)
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}