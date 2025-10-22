package com.example.botilleriaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.botilleriaapp.viewmodel.CarritoViewModel
import com.example.botilleriaapp.R
import com.example.botilleriaapp.model.Producto
import java.lang.Character.toString
import androidx.compose.runtime.collectAsState

@Composable
fun CarritoUI(viewModel: CarritoViewModel) { //UI de testing
    val carrito by viewModel.carrito.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val precio by viewModel.precio.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Carrito de Compras",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.gragas2),
            contentDescription = "Gragas",
            modifier = Modifier
                .width(250.dp)
                .height(175.dp)
        )

        OutlinedTextField(value = nombre, onValueChange = { viewModel.nombre.value = it }, label = { Text("Nombre") })
        OutlinedTextField(
            value = precio.toString(),
            onValueChange = { viewModel.precio.value = it.toIntOrNull() ?: 0 },
            label = { Text("Precio") } //El precio se transforma en string
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.agregarProducto(Producto(nombre = nombre, precio = precio))
            viewModel.nombre.value = ""; viewModel.precio.value = 0;
        }) {
            Text("Agregar Producto Testing")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(carrito) { carrito ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = carrito.nombre,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        carrito.precio.toString(), //Convierte el valor a string para mostrarlo
                        modifier = Modifier.weight(1f)
                    )
                }
                Divider()
            }
        }
    }
}