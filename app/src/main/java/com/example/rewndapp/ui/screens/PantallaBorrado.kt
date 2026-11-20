package com.example.rewndapp.ui.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import com.example.rewndapp.R
import com.example.rewndapp.model.Serie
import com.example.rewndapp.repositories.SerieRepository
import com.example.rewndapp.ui.theme.Fondo
import com.example.rewndapp.ui.theme.FondoCaratula
import com.example.rewndapp.ui.theme.FondoTarjeta
import com.example.rewndapp.ui.theme.Naranja
import com.example.rewndapp.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBorrado(
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit,
    serie: Serie
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
                        IconButton(onClick = onCancelar) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_atras),
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }

                        Text("Eliminar de mi biblioteca")

                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Borrar serie",
                            tint = Color.White
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "¿Eliminar ${serie.titulo} de tu biblioteca?",
                color = Color.White,
                fontSize = 20.sp
            )

            SerieABorrar(serie = serie)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FondoTarjeta)
                    .padding(16.dp)
            ) {
                Text("No se puede deshacer", fontWeight = FontWeight.Bold, color = Color.White)
                Text(
                    "Podrás volver a añadir ${serie.titulo}, pero tendrás que marcar de nuevo las temporadas como terminadas o en curso.",
                    fontSize = 14.sp,
                    color = TextoSecundario
                )
            }

            OutlinedButton(
                onClick = onConfirmar,
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Naranja)
            ) {
                Text(color = Color.Black, text = "Sí, eliminar de mi biblioteca")
            }
            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = FondoCaratula)
            ) {
                Text(color = Color.White, text = "Cancelar y conservar")
            }
        }
    }
}

@Composable
fun SerieABorrar(serie: Serie) {
    val comentariosVisibles = if (serie.temporadasTerminadas == 0) {
        "Ninguno"
    } else if (serie.temporadasTerminadas == 1) {
        "T1"
    } else {
        "T1 - T${serie.temporadasTerminadas}"
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
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
                Text(serie.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("${serie.estado.texto} · ${serie.progreso}", fontSize = 13.sp, color = TextoSecundario)
                Text("Comentarios visibles: $comentariosVisibles", fontSize = 13.sp, color = TextoSecundario)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BorradoPreview() {
    MaterialTheme {
        PantallaBorrado(
            onCancelar = {},
            onConfirmar = {},
            serie = SerieRepository.getSeries()[0],
        )
    }
}