package com.example.botilleriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.botilleriaapp.Model.Formulario
import com.example.botilleriaapp.ui.theme.BotilleriaAppTheme
import com.example.botilleriaapp.viewmodel.CarritoViewModel
import com.example.botilleriaapp.repository.CarritoRepository
import com.example.botilleriaapp.model.AppDatabase
import com.example.botilleriaapp.ui.*
import com.example.botilleriaapp.model.CarritoDao
import androidx.compose.runtime.getValue
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
                //Formulario()
                CarritoUI(viewModelCarrito)




                }
            }
        }

}


