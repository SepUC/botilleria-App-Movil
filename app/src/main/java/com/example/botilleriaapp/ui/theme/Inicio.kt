package com.example.botilleriaapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.botilleriaapp.R

// <--- 1. SE CREA UNA CLASE PARA GUARDAR EL NOMBRE Y LA IMAGEN
data class Product(val name: String, val imageId: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavegacionScreen() {
    // <--- LISTA ORIGINAL DE PRODUCTOS
    val products = listOf(
        Product("Cerveza Cristal", R.drawable.cristal),
        Product("Cerveza Escudo", R.drawable.escudo),
        Product("Cerveza Austral", R.drawable.austral),
        Product("Cerveza Kunstmann", R.drawable.kunstmann),
        Product("Pisco Mistral", R.drawable.mistral),
        Product("Mistral Ice", R.drawable.mistralice),
        Product("Vino tinto Gato", R.drawable.vinogato),
        Product("Whisky Blue Label", R.drawable.bluelabel)
    )

    // <--- ESTADO PARA GUARDAR EL TEXTO DE BÚSQUEDA
    var searchQuery by remember { mutableStateOf("") }

    // <--- SE FILTRA LA LISTA BASÁNDOSE EN LA BÚSQUEDA
    val filteredProducts = remember(searchQuery, products) {
        if (searchQuery.isBlank()) {
            products
        } else {
            products.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Botillería Donde Tito") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        // Se usa una Column para poner la barra de búsqueda sobre la grilla
        Column(modifier = Modifier.padding(innerPadding)) {
            // <--- BARRA DE BÚSQUEDA
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar producto...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                singleLine = true
            )

            // <--- LA GRILLA AHORA MUESTRA LOS PRODUCTOS FILTRADOS
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductItem(product = product)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Image(
            painter = painterResource(id = product.imageId),
            contentDescription = product.name,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { /* Lógica para comprar */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comprar")
        }
    }
}
