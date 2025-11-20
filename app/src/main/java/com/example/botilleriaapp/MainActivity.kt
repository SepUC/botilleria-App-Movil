package com.example.botilleriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.botilleriaapp.ui.theme.BotilleriaAppTheme
import com.example.botilleriaapp.ui.theme.Formulario
import com.example.botilleriaapp.ui.CarritoUI
import com.example.botilleriaapp.viewmodel.CarritoViewModel
import com.example.botilleriaapp.viewmodel.FormularioViewModel
import com.example.botilleriaapp.repository.CarritoRepository
import com.example.botilleriaapp.model.AppDatabase
import com.example.botilleriaapp.ui.*
import androidx.room.Room

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "carrito_db"
        ).build()
    }
    private val repository by lazy { CarritoRepository(db.carritoDao()) }
    private val viewModelCarrito by lazy { CarritoViewModel(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BotilleriaAppTheme {
                AppNavigation(viewModelCarrito)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModelCarrito: CarritoViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "formulario") {
        composable("formulario") {
            Formulario(viewModel = viewModel<FormularioViewModel>(), navController = navController)
        }
        composable("productos") {
            ProductListScreen(navController = navController, carritoViewModel = viewModelCarrito)
        }
        composable("carrito") {
            CarritoUI(viewModel = viewModelCarrito, navController = navController)
        }
    }
}