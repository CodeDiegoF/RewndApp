package com.example.rewndapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rewndapp.model.Serie
import com.example.rewndapp.data.listaSeries
import com.example.rewndapp.ui.theme.Fondo
import com.example.rewndapp.ui.theme.FondoCaratula
import com.example.rewndapp.ui.theme.FondoTarjeta
import com.example.rewndapp.ui.theme.Naranja
import com.example.rewndapp.ui.theme.TextoSecundario

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RewndApp()
            }
        }
    }
}

@Composable
fun RewndApp() {
    var serieSeleccionada by remember { mutableStateOf<Serie?>(null) }
    val serie = serieSeleccionada

    if (serie == null) {
        Biblioteca(onSerieClick = { serieSeleccionada = it })
    } else {
        Detalle(
            serie = serie,
            onVolver = { serieSeleccionada = null }
        )
    }
}

@Composable
fun Biblioteca(onSerieClick: (Serie) -> Unit = {}) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var serie by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Fondo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Cabecera(nombre = nombre)

            OutlinedTextField(
                value = serie,
                onValueChange = { textoNuevo -> serie = textoNuevo },
                label = { Text("Buscar serie en mi biblioteca", color = Color.White, fontSize = 14.sp) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
            ){
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Naranja)
                ){
                    Text(
                        color = Color.Black,
                        text = "Viendo"
                    )
                }
                OutlinedButton(
                    onClick = {},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = FondoCaratula)
                ){
                    Text(
                        color = Color.White,
                        text = "Por ver"
                    )
                }
                OutlinedButton(
                    onClick = {},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = FondoCaratula)
                ){
                    Text(
                        color = Color.White,
                        text = "Completadas"
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaSeries) { serieLista ->
                    TarjetaSerie(
                        serie = serieLista,
                        onClick = { onSerieClick(serieLista) }
                    )
                }
            }

            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Naranja)
            ){
                Text(
                    color = Color.Black,
                    text = " + Añadir serie"
                )
            }
        }

        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BarraInferior()
        }
    }
}


@Composable
fun Cabecera(nombre: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column (
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                color = Color.White, text = ("Mi Biblioteca"),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
        Text(
            color = Color.White, text = if (nombre.isBlank()) "Invitado" else "Hola, $nombre",
            style = MaterialTheme.typography.titleLarge,
        )
    }
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(FondoCaratula),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                color = Color.White, text = nombre.take(1).uppercase().ifEmpty { "?" },
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
fun TarjetaSerie(serie: Serie, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = FondoTarjeta)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 56.dp, height = 72.dp)
                    .background(FondoCaratula),
                contentAlignment = Alignment.Center
            ) {
                Text("Carátula", fontSize = 12.sp, color = TextoSecundario)
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(serie.titulo, fontSize = 20.sp, color = Color.White)
                Text("${serie.anio} - ${serie.generos}", fontSize = 13.sp, color = TextoSecundario)
                Text(serie.progreso, fontSize = 14.sp, color = Color.White)
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_flecha),
                contentDescription = "Ver detalle",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BarraInferior(){
    NavigationBar(
        containerColor = FondoTarjeta,
        contentColor = Color.White,
        tonalElevation = 10.dp
    ){
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_biblioteca), contentDescription = "Biblioteca") },
            label = { Text("Biblioteca") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.White,
                indicatorColor = Naranja,
                unselectedIconColor = TextoSecundario,
                unselectedTextColor = TextoSecundario
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_conversaciones), contentDescription = "Conversaciones") },
            label = { Text("Conversaciones") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.White,
                indicatorColor = Naranja,
                unselectedIconColor = TextoSecundario,
                unselectedTextColor = TextoSecundario
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_perfil), contentDescription = "Perfil") },
            label = { Text("Perfil") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.White,
                indicatorColor = Naranja,
                unselectedIconColor = TextoSecundario,
                unselectedTextColor = TextoSecundario
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BibliotecaPreview() {
    MaterialTheme {
        RewndApp()
    }
}


