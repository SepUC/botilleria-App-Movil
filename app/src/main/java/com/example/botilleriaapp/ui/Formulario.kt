package com.example.botilleriaapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.botilleriaapp.viewmodel.FormularioViewModel
import com.example.botilleriaapp.R

@Composable
fun Formulario(
    viewModel: FormularioViewModel, 
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenidos ", color = Color.Black)
        Image(
            painter = painterResource(id = R.drawable.pantallazo1),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text("Ingrese sus datos para iniciar sesión", color = Color.Black)

        OutlinedTextField(
            value = viewModel.formulario.nombre,
            onValueChange = { viewModel.formulario.nombre = it },
            label = { Text("Ingresa nombre") },
            isError = !viewModel.verificarNombre(),
            supportingText = { Text(viewModel.mensajesError.nombre, color = Color.Red) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        OutlinedTextField(
            value = viewModel.formulario.correo,
            onValueChange = { viewModel.formulario.correo = it },
            label = { Text("Ingresa correo") },
            isError = !viewModel.verificarCorreo(),
            supportingText = { Text(viewModel.mensajesError.correo, color = Color.Red) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        OutlinedTextField(
            value = viewModel.formulario.edad,
            onValueChange = { viewModel.formulario.edad = it },
            label = { Text("Ingresa edad") },
            isError = !viewModel.verificarEdad(),
            supportingText = { Text(viewModel.mensajesError.edad, color = Color.Red) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        Checkbox(
            checked = viewModel.formulario.terminos,
            onCheckedChange = { viewModel.formulario.terminos = it },
        )
        Text("Acepta los términos")

        Button(
            enabled = viewModel.verificarFormulario(),
            onClick = {
                if (viewModel.verificarFormulario()) {
                    onLoginSuccess() // Se llama a la función para guardar la sesión
                    navController.navigate("productos")
                }
            }
        ) {
            Text("Iniciar Sesión")
        }
    }
}