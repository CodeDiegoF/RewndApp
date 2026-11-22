package com.example.rewndapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.rewndapp.R
import com.example.rewndapp.repositories.SerieRepository
import com.example.rewndapp.model.Serie
import com.example.rewndapp.ui.theme.Fondo
import com.example.rewndapp.ui.theme.FondoCaratula
import com.example.rewndapp.ui.theme.FondoTarjeta
import com.example.rewndapp.ui.theme.Naranja
import com.example.rewndapp.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    serie: Serie,
    onVolver: () -> Unit,
    onEliminar: () -> Unit,
    onEditar: () -> Unit
) {
    Scaffold(
        containerColor = Fondo,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(onClick = onVolver) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_atras),
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                        Text(
                            color = Color.White,
                            text = "Detalle de la serie",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Fondo,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = { BarraInferior() }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Fondo)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Fondo)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(
                        modifier = Modifier
                            .size(width = 110.dp, height = 150.dp)
                            .background(FondoCaratula),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = serie.imagen,
                            contentDescription = "Carátula de ${serie.titulo}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(serie.titulo, fontSize = 32.sp, color = Color.White)
                        Text(serie.pais, fontSize = 14.sp, color = TextoSecundario)
                        Text(
                            "${serie.totalTemporadas} temporadas · ${serie.episodios} episodios",
                            fontSize = 14.sp,
                            color = TextoSecundario
                        )
                        Text(serie.idioma, fontSize = 14.sp, color = TextoSecundario)
                    }
                }

                Text(serie.generos, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(serie.sinopsis, fontSize = 16.sp, color = TextoSecundario)

                Text("Ver Trailer", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(FondoTarjeta),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tráiler", color = TextoSecundario)
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Naranja)
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        painter = painterResource(id = R.drawable.ic_conversaciones),
                        contentDescription = "Abrir conversación sin spoilers",
                        tint = Color.Black
                    )
                    Text(
                        color = Color.Black,
                        fontSize = 16.sp,
                        text = "Abrir conversación sin spoilers")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onEditar,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = FondoCaratula)
                    ) {
                        Text(color = Color.White, text = "Editar progreso", maxLines = 1)
                    }
                    OutlinedButton(
                        onClick = onEliminar,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = FondoCaratula)
                    ) {
                        Text(color = Color.White, text = "Eliminar", maxLines = 1)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetallePreview() {
    MaterialTheme {
        PantallaDetalle(
            serie = SerieRepository.getSeries()[0],
            onVolver = {},
            onEditar = {},
            onEliminar = {}
        )
    }
}