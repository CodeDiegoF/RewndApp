package com.example.rewndapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rewndapp.model.Serie
import com.example.rewndapp.model.Estado
import com.example.rewndapp.data.listaSeries
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Biblioteca()
            }
        }
    }
}

@Composable
fun Biblioteca() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var resumen by rememberSaveable { mutableStateOf("") }
    var serie by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CabeceraFicha(nombre = nombre)

        OutlinedTextField(
            value = serie,
            onValueChange = { textoNuevo -> serie = textoNuevo },
            label = { Text("Buscar serie") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        if (resumen.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = resumen,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}


@Composable
fun CabeceraFicha(nombre: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ){
        Column {
            Text(
                text = "Mi Biblioteca",
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(120.dp),
    ) {
        Column {
        Text(
            text = if (nombre.isBlank()) "Invitado" else "Hola, $nombre",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "4 seires en la biblioteca",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = nombre.take(1).uppercase().ifEmpty { "?" },
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BibliotecaPreview() {
    MaterialTheme {
        Biblioteca()
    }
}


