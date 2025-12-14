package com.example.botilleriaapp

import android.content.Context
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
        )
        .fallbackToDestructiveMigration() // ¡Solución para que la app no se cierre!
        .build()
    }
    private val repository by lazy { CarritoRepository(db.carritoDao()) }
    private val viewModelCarrito by lazy { CarritoViewModel(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("botilleria_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false)

        enableEdgeToEdge()
        setContent {
            BotilleriaAppTheme {
                AppNavigation(
                    viewModelCarrito = viewModelCarrito,
                    isLoggedIn = isLoggedIn,
                    onLoginSuccess = {
                        prefs.edit().putBoolean("IS_LOGGED_IN", true).apply()
                    },
                    onLogout = {
                        prefs.edit().putBoolean("IS_LOGGED_IN", false).apply()
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    viewModelCarrito: CarritoViewModel,
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    // Se cambia el startDestination para que siempre inicie en el formulario
    val startDestination = "formulario"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("formulario") {
            Formulario(
                viewModel = viewModel<FormularioViewModel>(),
                navController = navController,
                onLoginSuccess = onLoginSuccess
            )
        }
        composable("productos") {
            ProductListScreen(
                navController = navController,
                carritoViewModel = viewModelCarrito,
                onLogout = onLogout
            )
        }
        composable("carrito") {
            CarritoUI(viewModel = viewModelCarrito, navController = navController)
        }
    }
}