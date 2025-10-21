package com.example.botilleriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.botilleriaapp.ViewModel.FormularioViewModel
import com.example.botilleriaapp.ui.theme.BotilleriaAppTheme
import com.example.botilleriaapp.ui.theme.Formulario
import com.example.botilleriaapp.ui.theme.NavegacionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BotilleriaAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "formulario") {
        composable("formulario") {
            Formulario(viewModel = FormularioViewModel(), navController = navController)
        }
        composable("navegacion") {
            NavegacionScreen()
        }
    }
}
