package com.example.botilleriaapp.viewmodel

import com.example.botilleriaapp.model.Post
import com.example.botilleriaapp.repository.PostRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Clase de prueba para el PostViewModel, usando JUnit 5.
 */
@ExperimentalCoroutinesApi
class PostViewModelTest {

    // 1. Creamos un dispatcher de prueba para controlar las coroutines
    private val testDispatcher = UnconfinedTestDispatcher()

    // 2. Declaramos las variables que vamos a usar
    private lateinit var repository: PostRepository
    private lateinit var viewModel: PostViewModel

    // 3. @BeforeEach se ejecuta antes de cada test
    @BeforeEach
    fun setUp() {
        // Reemplazamos el hilo principal de Android por nuestro dispatcher de prueba
        Dispatchers.setMain(testDispatcher)

        // Creamos un "mock" (un doble de prueba) del repositorio
        repository = mockk()
        // Creamos el ViewModel con el repositorio falso
        viewModel = PostViewModel(repository)
    }

    // 4. @AfterEach se ejecuta después de cada test para limpiar
    @AfterEach
    fun tearDown() {
        // Restauramos el hilo principal original
        Dispatchers.resetMain()
    }

    // 5. Escribimos nuestro caso de prueba con la anotación de JUnit 5
    @Test
    fun `debería cargar la lista de posts correctamente`() {
        // Arrange (Preparar): Definimos los datos falsos y el comportamiento del mock
        val fakePosts = listOf(
            Post(1, "Título 1", "Contenido 1"),
            Post(2, "Título 2", "Contenido 2")
        )
        // Le decimos a MockK: "Cuando se llame a repository.getPosts(), devuelve la lista falsa"
        coEvery { repository.getPosts() } returns fakePosts

        // Act (Actuar): Ejecutamos la función que queremos probar
        viewModel.loadPosts()

        // Assert (Afirmar): Comprobamos que el resultado es el esperado
        // El valor del StateFlow de posts en el ViewModel debería ser igual a nuestra lista falsa
        viewModel.posts.value shouldBe fakePosts
    }

    @Test
    fun `posts debe iniciar con lista vacía`() {
        // Assert
        viewModel.posts.value shouldBe emptyList()
    }

    @Test
    fun `loadPosts con error debe retornar lista vacía`() {
        // Arrange
        coEvery { repository.getPosts() } throws Exception("Error de red")

        // Act
        viewModel.loadPosts()

        // Assert
        viewModel.posts.value shouldBe emptyList()
    }

    @Test
    fun `loadPosts debe actualizar el estado de posts`() {
        // Arrange
        val posts1 = listOf(Post(1, "Post 1", "Contenido 1"))
        val posts2 = listOf(
            Post(2, "Post 2", "Contenido 2"),
            Post(3, "Post 3", "Contenido 3")
        )
        
        coEvery { repository.getPosts() } returns posts1

        // Act
        viewModel.loadPosts()

        // Assert
        viewModel.posts.value shouldBe posts1

        // Arrange - cambiar el comportamiento del mock
        coEvery { repository.getPosts() } returns posts2

        // Act - volver a cargar
        viewModel.loadPosts()

        // Assert - debe tener los nuevos datos
        viewModel.posts.value shouldBe posts2
    }

    @Test
    fun `loadPosts con lista vacía desde repositorio`() {
        // Arrange
        coEvery { repository.getPosts() } returns emptyList()

        // Act
        viewModel.loadPosts()

        // Assert
        viewModel.posts.value shouldBe emptyList()
    }

    @Test
    fun `loadPosts con un solo post`() {
        // Arrange
        val singlePost = listOf(Post(1, "Único Post", "Contenido único"))
        coEvery { repository.getPosts() } returns singlePost

        // Act
        viewModel.loadPosts()

        // Assert
        viewModel.posts.value shouldBe singlePost
        viewModel.posts.value.size shouldBe 1
    }

    @Test
    fun `loadPosts con múltiples posts`() {
        // Arrange
        val multiplePosts = listOf(
            Post(1, "Post 1", "Contenido 1"),
            Post(2, "Post 2", "Contenido 2"),
            Post(3, "Post 3", "Contenido 3"),
            Post(4, "Post 4", "Contenido 4"),
            Post(5, "Post 5", "Contenido 5")
        )
        coEvery { repository.getPosts() } returns multiplePosts

        // Act
        viewModel.loadPosts()

        // Assert
        viewModel.posts.value shouldBe multiplePosts
        viewModel.posts.value.size shouldBe 5
    }
}
