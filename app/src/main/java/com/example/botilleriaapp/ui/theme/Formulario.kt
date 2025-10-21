package com.example.botilleriaapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.botilleriaapp.ViewModel.FormularioViewModel
import com.example.botilleriaapp.R

@Composable
fun Formulario(viewModel: FormularioViewModel, navController: NavController) {


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
            supportingText = { Text(viewModel.mensajesError.nombre, color = Color.Red) }
        )
        OutlinedTextField(
            value = viewModel.formulario.correo,
            onValueChange = { viewModel.formulario.correo = it },
            label = { Text("Ingresa correo") },
            isError = !viewModel.verificarCorreo(),
            supportingText = { Text(viewModel.mensajesError.correo, color = Color.Red) }
        )
        OutlinedTextField(
            value = viewModel.formulario.edad,
            onValueChange = { viewModel.formulario.edad = it },
            label = { Text("Ingresa edad") },
            isError = !viewModel.verificarEdad(),
            supportingText = { Text(viewModel.mensajesError.edad, color = Color.Red) }
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
                    navController.navigate("navegacion")
                }
            }
        ) {
            Text("Iniciar Sesión")
        }
    }
}
