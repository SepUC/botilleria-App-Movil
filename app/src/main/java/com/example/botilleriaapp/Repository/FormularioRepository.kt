package com.example.botilleriaapp.Repository

import com.example.botilleriaapp.Model.FormularioModel
import com.example.botilleriaapp.Model.MensajeError

class FormularioRepository{

    private var formulario = FormularioModel()
    private var error = MensajeError()

    fun getFormulario():  FormularioModel = formulario
    fun getMensajesError():  MensajeError = error


    fun cambiarNombre(nuevoNombre: String) {
        formulario.nombre = nuevoNombre
    }

    fun validacionNombre(): Boolean {
        if(formulario.nombre=="")
            return false
        else
            return true
    }

    fun validacionCorreo(): Boolean {
        if (!formulario.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$")))
            return false
        else
            return true
    }

    fun validacionEdad(): Boolean {
        val edadInt = formulario.edad.toIntOrNull()
        if (edadInt == null || edadInt < 0 || edadInt > 120)
            return false
        else
            return true
    }

    fun validacionTerminos(): Boolean {
        if (!formulario.terminos)
            return false
        else
            return true
    }






}