package com.example.rewndapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rewndapp.R
import com.example.rewndapp.RewndApp
import com.example.rewndapp.model.Serie
import com.example.rewndapp.repositories.SerieRepository
import com.example.rewndapp.ui.theme.Fondo
import com.example.rewndapp.ui.theme.FondoCaratula
import com.example.rewndapp.ui.theme.FondoTarjeta
import com.example.rewndapp.ui.theme.Naranja
import com.example.rewndapp.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAdicion(
    serieEncontrada: Serie?,
    textoBusqueda: String,
    resultados: List<Serie>,
    onBuscar: (String) -> Unit,
    onSeleccionar: (Serie) -> Unit,
    onAnadir: () -> Unit,
    onVolver: () -> Unit
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
                        Text("Añadir Serie")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Fondo,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = { BarraInferior() }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Fondo)
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                item{
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { texto -> onBuscar(texto)},
                        placeholder = { Text("Buscar nueva serie", color = TextoSecundario) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                        },
                        trailingIcon = {
                            if (textoBusqueda.isNotEmpty()) {
                                IconButton(onClick = { onBuscar("") }) {
                                    Icon(Icons.Default.Close, contentDescription = "Limpiar", tint = Color.White)
                                }
                            }
                        },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                }

                if (textoBusqueda.isNotBlank()){
                    item {
                        val textoResultados = if (resultados.isEmpty()) {
                            "No se han encontrado series"
                        } else {
                            "${resultados.size} resultados"
                        }
                        Text(
                            textoResultados,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                items(resultados, key = { it.id }) { serie ->
                    SerieEncontrada(
                        serie = serie,
                        onClick = { onSeleccionar(serie) },
                        seleccionada = serie.id == serieEncontrada?.id
                    )
                }


                if (serieEncontrada != null) {
                    item {
                        Text(
                            text = "${serieEncontrada?.titulo} - Serie seleccionada",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = "${serieEncontrada?.sinopsis}",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .background(FondoTarjeta)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = "Protección por temporadas",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "En «Por ver» no hay temporadas terminadas: todos" +
                                            "sus comentarios están ocultos. Puedes revelar cada" +
                                            "comentario con «Ver spoiler».",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedButton(
                        onClick = onAnadir,
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
            }
        }
    }
}

@Composable
fun SerieEncontrada(
    serie: Serie,
    onClick: () -> Unit,
    seleccionada: Boolean
) {
    OutlinedCard(
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
                AsyncImage(
                    model = serie.imagen,
                    contentDescription = "Carátula de ${serie.titulo}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(serie.titulo, fontSize = 20.sp, color = Color.White)
                Text("${serie.anio} - ${serie.pais}", color = TextoSecundario)
                Text("${serie.generos}", color = TextoSecundario)
            }
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(1.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (seleccionada) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Seleccionada",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdicionPreview() {
    val series = SerieRepository.getSeries()
    MaterialTheme {
        PantallaAdicion(
            textoBusqueda = "bea",
            resultados = series.take(2),
            serieEncontrada = series[0],
            onBuscar = {},
            onSeleccionar = {},
            onAnadir = {},
            onVolver = {}
        )
    }
}