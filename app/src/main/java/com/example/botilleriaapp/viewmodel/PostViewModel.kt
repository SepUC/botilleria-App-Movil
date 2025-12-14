package com.example.botilleriaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botilleriaapp.model.Post
import com.example.botilleriaapp.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la lógica de negocio para la pantalla de Posts.
 */
class PostViewModel(private val repository: PostRepository) : ViewModel() {

    // Un StateFlow privado para guardar el estado interno de la lista de posts
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    // Un StateFlow público e inmutable para que la UI lo observe
    val posts = _posts.asStateFlow()

    /**
     * Carga la lista de posts desde el repositorio y actualiza el StateFlow.
     */
    fun loadPosts() {
        viewModelScope.launch {
            try {
                // Llama al repositorio para obtener los datos
                val postList = repository.getPosts()
                // Actualiza el valor del StateFlow con los nuevos datos
                _posts.value = postList
            } catch (e: Exception) {
                // En una app real, aquí manejaríamos los errores (ej. mostrar un mensaje en la UI)
                _posts.value = emptyList() // En caso de error, dejamos la lista vacía
            }
        }
    }
}
