package com.example.botilleriaapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
// Importación corregida para usar el Producto del CARRITO, no el de la API
import com.example.botilleriaapp.model.Producto 
import com.example.botilleriaapp.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoUI(viewModel: CarritoViewModel, navController: NavController) {
    val carrito by viewModel.carrito.collectAsState()
    val totalCarrito = carrito.sumOf { (it.precio * it.cantidad).toDouble() }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { 
                showDialog = false 
                // Opcional: navegar hacia atrás después de confirmar
                // navController.popBackStack()
            },
            title = { Text("¡Gracias por tu compra!") },
            text = { Text("Tu compra ha sido realizada correctamente.") },
            confirmButton = {
                Button(
                    onClick = { 
                        showDialog = false
                        // Opcional: navegar hacia atrás después de confirmar
                        // navController.popBackStack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Volver")
                    }
                }
            )
        },
        bottomBar = {
            if (carrito.isNotEmpty()) {
                BottomAppBar {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total: $$totalCarrito",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = { 
                            viewModel.vaciarCarrito()
                            showDialog = true
                        }) {
                            Text("Pagar")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (carrito.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío", style = MaterialTheme.typography.headlineSmall)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f), // Usa weight para que ocupe el espacio disponible
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(carrito) { producto ->
                        CarritoItem(
                            producto = producto,
                            onUpdate = { viewModel.actualizarProducto(it) },
                            onDelete = { viewModel.eliminarProducto(it) }
                        )
                        Divider()
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.vaciarCarrito() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Vaciar Carrito")
                }
            }
        }
    }
}

@Composable
fun CarritoItem(
    producto: Producto,
    onUpdate: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Total: $${producto.precio * producto.cantidad}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = {
                        if (producto.cantidad > 1) {
                            onUpdate(producto.copy(cantidad = producto.cantidad - 1))
                        } else {
                            onDelete(producto)
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Quitar uno")
                }

                Text(text = producto.cantidad.toString(), style = MaterialTheme.typography.titleMedium)

                IconButton(
                    onClick = { onUpdate(producto.copy(cantidad = producto.cantidad + 1)) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir uno")
                }

                IconButton(
                    onClick = { onDelete(producto) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar producto")
                }
            }
        }
    }
}