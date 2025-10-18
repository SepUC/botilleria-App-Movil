package com.example.botilleriaapp.Model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.botilleriaapp.R


@Composable
fun Formulario() {

    var ma by remember{mutableStateOf(false)}
    var username by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter= painterResource(id = R.drawable.gragas2),
            contentDescription="Logo de la app",
            modifier=Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )





        Text(text = "nombre")
        //input1
        TextField(
            value=username,
            onValueChange = {username=it},
            modifier=Modifier.fillMaxWidth().padding(horizontal=16.dp),
            placeholder={Text(text="Escribe tu nombre")}
        )
        Text(text = "password")
        //input2
        TextField(
            value=password,
            onValueChange = {password=it},
            modifier=Modifier.fillMaxWidth().padding(horizontal=16.dp),
            placeholder={Text(text="Escribe tu clave papu")}
        )
        Button(onClick = {ma=true}) {
            Text(text = "Enviar")
        }
        if (ma) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("hola que tal") },
                text = { Text("te falta una coma") },
                confirmButton = {
                    TextButton(onClick = {}) {
                        Text("ok")
                    }
                }
            )
        }
    }
}