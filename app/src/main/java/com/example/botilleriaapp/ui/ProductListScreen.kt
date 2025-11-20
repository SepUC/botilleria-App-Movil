package com.example.botilleriaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter

@Composable
fun ProductListScreen(productoViewModel: ProductoViewModel = viewModel()) {
    val productos by productoViewModel.productos.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(productos) { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row {
                    // Safely access the URL from the CoverImage object
                    producto.cover_image?.url?.let { imageUrl ->
                        Image(
                            painter = rememberImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(8.dp)) {
                        producto.name?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
                        producto.price?.let { Text(text = "$$it", style = MaterialTheme.typography.bodyMedium) }
                    }
                }
            }
        }
    }
}