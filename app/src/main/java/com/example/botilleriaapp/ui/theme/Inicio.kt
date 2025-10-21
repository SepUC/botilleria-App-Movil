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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
    // <--- 2. AHORA LA LISTA CONTIENE OBJETOS `Product` (NOMBRE E IMAGEN)
    // Reemplaza los nombres e imágenes con los tuyos.
    val products = listOf(
        Product("Cerveza Cristal", R.drawable.cristal), // Reemplaza nombre e imagen
        Product("Cerveza Escudo", R.drawable.escudo), // Reemplaza nombre e imagen
        Product("Cerveza Austral", R.drawable.austral), // Reemplaza nombre e imagen
        Product("Cerveza Kunstmann", R.drawable.kunstmann), // Reemplaza nombre e imagen
        Product("Pisco Mistral", R.drawable.mistral), // Reemplaza nombre e imagen
        Product("Mistral Ice", R.drawable.mistralice), // Reemplaza nombre e imagen
        Product("Vino tinto Gato", R.drawable.vinogato), // Reemplaza nombre e imagen
        Product("Whisky Blue Label", R.drawable.bluelabel)  // Reemplaza nombre e imagen
    )

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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // <--- 3. LA GRILLA AHORA USA TU LISTA DE PRODUCTOS
            items(products) { product ->
                // Se pasa el objeto Product completo al ProductItem
                ProductItem(product = product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) { // <--- 4. AHORA ACEPTA UN OBJETO `Product`
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // <--- 5. SE AÑADE EL TEXTO CON EL NOMBRE DEL PRODUCTO
        Text(
            text = product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Image(
            painter = painterResource(id = product.imageId),
            contentDescription = product.name, // Mejoramos la accesibilidad
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
