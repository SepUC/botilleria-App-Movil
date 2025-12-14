package com.example.botilleriaapp.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FormularioViewModelTest {

    private lateinit var viewModel: FormularioViewModel

    // Esta función se ejecuta ANTES de cada prueba
    @Before
    fun setUp() {
        // Arrange (Preparar): Creamos una nueva instancia limpia para cada test
        viewModel = FormularioViewModel()
    }

    // Usamos @Test para marcar una función como una prueba
    @Test
    fun `verificarCorreo con correo válido devuelve true`() {
        // Arrange (Preparar)
        viewModel.formulario.correo = "test@dominio.com"

        // Act (Actuar): Llamamos a la función que queremos probar
        val resultado = viewModel.verificarCorreo()

        // Assert (Afirmar): Comprobamos que el resultado es el que esperamos
        org.junit.Assert.assertTrue("El correo debería ser válido", resultado)
    }

    @Test
    fun `verificarCorreo con correo inválido (sin @) devuelve false`() {
        // Arrange
        viewModel.formulario.correo = "testdominio.com"

        // Act
        val resultado = viewModel.verificarCorreo()

        // Assert
        org.junit.Assert.assertFalse("El correo sin @ debería ser inválido", resultado)
    }

    @Test
    fun `verificarCorreo con correo vacío devuelve false`() {
        // Arrange
        viewModel.formulario.correo = ""

        // Act
        val resultado = viewModel.verificarCorreo()

        // Assert
        org.junit.Assert.assertFalse("El correo vacío debería ser inválido", resultado)
    }
}