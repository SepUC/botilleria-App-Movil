package com.example.botilleriaapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.botilleriaapp.Repository.FormularioRepository
import com.example.botilleriaapp.Model.FormularioModel
import com.example.botilleriaapp.Model.MensajeError



class formularioViewModel: ViewModel(){
    private val repository = FormularioRepository()

    var formulario: FormularioModel by mutableStateOf( repository.getFormulario() )
    var mensajesError: MensajeError by mutableStateOf( repository.getMensajesError() )

    fun verificarFormulario(): Boolean {
        return verificarNombre() &&
                verificarCorreo() &&
                verificarEdad()

    }

    fun verificarNombre(): Boolean {
        if (!repository.validacionNombre()) {
            mensajesError.nombre = "El nombre no puede estar vacío"
            return false
        } else {
            mensajesError.nombre = ""
            return true
        }
        return repository.validacionNombre()
    }

    fun verificarCorreo(): Boolean {
        if(!repository.validacionCorreo()) {
            mensajesError.correo = "El correo no es válido"
            return false
        } else {
            mensajesError.correo = ""
            return true
        }
        return repository.validacionCorreo()
    }

    fun verificarEdad(): Boolean {
        if(!repository.validacionEdad()) {
            mensajesError.edad = "La edad debe ser un número entre 0 y 120"
            return false
        } else {
            mensajesError.edad = ""
            return true
        }
        return repository.validacionEdad()
    }


    }
