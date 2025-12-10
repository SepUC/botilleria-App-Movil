package com.example.botilleriaapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.botilleriaapp.viewmodel.ProductoViewModel
import com.example.botilleriaapp.viewmodel.CarritoViewModel
import com.example.botilleriaapp.model.Producto as CarritoProducto
import kotlinx.coroutines.launch

@Composable
private fun ProductQuantitySelector(onAddToCart: (Int) -> Unit) {
    var quantity by remember { mutableStateOf(1) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = { if (quantity > 1) quantity-- },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Quitar uno")
            }

            Text(text = quantity.toString(), style = MaterialTheme.typography.titleMedium)

            IconButton(
                onClick = { quantity++ },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir uno")
            }
        }
        Button(
            onClick = { 
                onAddToCart(quantity)
                quantity = 1 // Reset quantity after adding to cart
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (quantity > 1) "Agregar $quantity al carrito" else "Agregar al carrito")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel = viewModel(),
    carritoViewModel: CarritoViewModel,
    onLogout: () -> Unit
) {
    val productos by productoViewModel.productos.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    var showDialog by remember { mutableStateOf(false) }
    var lastAddedProduct by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Todos") + productos.mapNotNull { it.category }.distinct().sorted()

    val filteredProductos = productos.filter {
        (selectedCategory == "Todos" || it.category == selectedCategory) &&
                (searchQuery.isBlank() || it.name.orEmpty().contains(searchQuery, ignoreCase = true))
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¡Éxito!") },
            text = { Text("Se ha agregado '${lastAddedProduct}' al carrito correctamente.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Categorías", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))
                Divider()
                categories.forEach { category ->
                    NavigationDrawerItem(
                        label = { Text(category) },
                        selected = category == selectedCategory,
                        onClick = {
                            selectedCategory = category
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Productos") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    },
                    actions = {
                        TextButton(onClick = {
                            onLogout()
                            navController.navigate("formulario") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }) {
                            Text("Cerrar sesión")
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    Button(
                        onClick = { navController.navigate("carrito") },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                        Text("Ver carrito")
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar productos") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp), // Aumentamos el tamaño mínimo para que quepa el contador
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp) // Aumentamos el espaciado vertical
                ) {
                    items(filteredProductos) { producto ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween // Ajustamos el espaciado
                            ) {
                                AsyncImage(
                                    model = producto.cover_image?.url,
                                    contentDescription = producto.name,
                                    modifier = Modifier
                                        .size(100.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                producto.name?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.labelSmall,
                                        maxLines = 2,
                                        minLines = 2
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                producto.price?.let {
                                    Text(
                                        text = "$$it",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                ProductQuantitySelector { quantity ->
                                    val productoParaCarrito = CarritoProducto(
                                        nombre = producto.name ?: "",
                                        precio = producto.price ?: 0,
                                        cantidad = quantity
                                    )
                                    carritoViewModel.agregarProducto(productoParaCarrito)
                                    lastAddedProduct = "${quantity}x ${producto.name}"
                                    showDialog = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}