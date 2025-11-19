package com.example.botilleriaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.botilleriaapp.model.Producto
import com.example.botilleriaapp.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch
import com.example.botilleriaapp.viewmodel.ProductosDataViewModel

// <--- SE AÑADE CATEGORÍA AL PRODUCTO
data class Product(val name: String, val precio: Int, val imageId: Int, val category: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavegacionScreen(navController: NavController, viewModel: CarritoViewModel, viewModel2: ProductosDataViewModel) {
    // LISTA DE PRODUCTOS AHORA CON CATEGORÍA -> Cambiar al Model - Listar desde la API los productos
    val products by viewModel2.productosData.collectAsState(initial = emptyList());

    // <--- LISTA DE CATEGORÍAS -> Model
    val categories = listOf("Todos", "Cervezas", "Licores", "Vinos","Bebidas","Snacks","Otros","Promociones")

    // Estados para la búsqueda y la categoría seleccionada
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }



    // Lógica de filtrado doble: por categoría y luego por búsqueda -> CAMBIAR A Viewmodel
    val filteredProducts = remember(searchQuery, selectedCategory, products) {
        val categoryFiltered = if (selectedCategory == "Todos") {
            products
        } else {// Filtrado por categoría
            products.filter { it.category == selectedCategory }
        }
        // Filtrado por búsqueda dentro de la categoría seleccionada
        if (searchQuery.isBlank()) {
            categoryFiltered
        } else {// Filtrado por búsqueda
            categoryFiltered.filter {// Filtrado por búsqueda
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    // Controladores para el menú lateral
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // Estructura principal con menú lateral, barra superior e inferior
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {// Contenido del menú lateral
                Text("Categorías", modifier = Modifier.padding(16.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                categories.forEach { category ->// Ítems del menú lateral
                    NavigationDrawerItem(// Ítem del menú lateral
                        label = { Text(category) },
                        selected = category == selectedCategory,
                        onClick = {// Acción al seleccionar una categoría
                            selectedCategory = category
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {// Contenido principal de la pantalla
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Botillería Donde Tito") },
                    // <--- ICONO PARA ABRIR EL MENÚ
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.LightGray,
                        titleContentColor = Color.Black
                    )
                )
            },// Barra inferior con botón al carrito
            bottomBar = {
                BottomAppBar(containerColor = Color.LightGray) {
                    Button(
                        onClick = { navController.navigate("carrito") },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Ir al Carrito")
                    }
                }
            }// Contenido principal con campo de búsqueda y rejilla de productos
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar producto...") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    singleLine = true
                )
                // Rejilla de productos filtrados
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductItem(product, viewModel)
                    }
                }
            }
        }
    }
}
// Composable para mostrar cada producto individualmente
@Composable
fun ProductItem(product: com.example.botilleriaapp.data.model.Producto, viewModel: CarritoViewModel) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¡Producto Agregado!") },
            text = { Text("Se ha agregado ${product.name} al carrito.") },
            confirmButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Image(
            painter = painterResource(id = product.id),
            contentDescription = product.name,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                viewModel.agregarProducto(Producto(nombre = product.name, precio = product.price))
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comprar")
        }
    }
}
