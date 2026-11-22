package com.example.rewndapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.rewndapp.model.Estado
import com.example.rewndapp.model.Serie
import com.example.rewndapp.repositories.SerieRepository
import com.example.rewndapp.ui.theme.Fondo
import com.example.rewndapp.ui.theme.FondoCaratula
import com.example.rewndapp.ui.theme.FondoTarjeta
import com.example.rewndapp.ui.theme.Naranja
import com.example.rewndapp.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEdicion(
    serie: Serie,
    error: String?,
    onVolver: () -> Unit,
    onGuardar: (Estado, Int, Boolean) -> Unit,
    onLimpiarError: () -> Unit
) {
    var estado by remember { mutableStateOf(serie.estado) }

    var temporada by remember {
        mutableStateOf(
            if (serie.temporadaEnCurso > 0) serie.temporadaEnCurso
            else if (serie.temporadasTerminadas > 0) serie.temporadasTerminadas
            else 1
        ) // para esto tambien he investigado si se pueden poner if dentro de los mutableStateOf
    }

    var terminada by remember {
        mutableStateOf(serie.temporadasTerminadas > serie.temporadaEnCurso)
    }

    var desplegado by remember { mutableStateOf(false) }

    // he tenido que investigar también como limpiar errores para que no se queden de forma permanente
    LaunchedEffect(estado, temporada, terminada) {
        onLimpiarError()
    }
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
                        Text("Editar ${serie.titulo}")
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
                .background(color = Fondo)
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Progreso:", color = Color.White, fontWeight = FontWeight.Bold)

            Text("Estado en mi biblioteca:",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                OutlinedButton(
                    onClick = { estado = Estado.VIENDO },
                    modifier = Modifier.weight(1f),
                    shape = RectangleShape,
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estado == Estado.VIENDO) Naranja else FondoCaratula
                    )
                ) {
                    Text(
                        color = if (estado == Estado.VIENDO) Color.Black else Color.White,
                        text = "Viendo"
                    )
                }
                OutlinedButton(
                    onClick = { estado = Estado.POR_VER },
                    shape = RectangleShape,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estado == Estado.POR_VER) Naranja else FondoCaratula
                    )
                ) {
                    Text(
                        color = Color.White,
                        text = "Por ver"
                    )
                }
                OutlinedButton(
                    onClick = { estado = Estado.COMPLETADA },
                    shape = RectangleShape,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estado == Estado.COMPLETADA) Naranja else FondoCaratula
                    )
                ) {
                    Text(
                        color = if (estado == Estado.COMPLETADA) Color.Black else Color.White,
                        text = "Completadas"
                    )
                }
            }

            Text("Temporada", color = Color.White, fontWeight = FontWeight.Bold)
            Box {
                OutlinedButton(
                    onClick = { desplegado = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = FondoCaratula)
                ) {
                    Text("T$temporada", color = Color.White, modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Elegir temporada",
                        tint = Color.White
                    )
                }
                // para utilizar el DropdownMenu he tenido que onvestigar un poco para matizar conceptos y su funcionamiento
                DropdownMenu(
                    expanded = desplegado,
                    onDismissRequest = { desplegado = false }
                ) {
                    for (numero in 1..serie.totalTemporadas) {
                        DropdownMenuItem(
                            text = { Text("T$numero") },
                            onClick = {
                                temporada = numero
                                desplegado = false
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { terminada = true },
                    modifier = Modifier.weight(1f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (terminada) Naranja else FondoCaratula
                    )
                ) {
                    Text(
                        color = if (terminada) Color.Black else Color.White,
                        text = "Terminada"
                    )
                }
                OutlinedButton(
                    onClick = { terminada = false },
                    modifier = Modifier.weight(1f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!terminada) Naranja else FondoCaratula
                    )
                ) {
                    Text(
                        color = if (!terminada) Color.Black else Color.White,
                        text = "En curso"
                    )
                }
            }

            if (error != null) {
                Text(error, color = Color(0xFFFF6B6B), fontSize = 14.sp)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(FondoTarjeta)
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_aviso),
                        contentDescription = "Ver spoiler",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Ver spoiler: revela un comentario de una temporada sin terminar sin cambiar tu progreso.",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(FondoTarjeta)
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color.White)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Tu límite sin spoiler", fontWeight = FontWeight.Bold, color = Color.White)
                        Text(
                            "Solo verás comentarios de temporadas terminadas. Si marcas una temporada como terminada, desbloquearás sus comentarios.",
                            fontSize = 14.sp,
                            color = TextoSecundario
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedButton(
                        onClick = { onGuardar(estado, temporada, terminada) },
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Naranja)
                    ) {
                        Text(color = Color.Black, text = "Guardar cambios")
                    }
                    OutlinedButton(
                        onClick = onVolver,
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = FondoCaratula)
                    ) {
                        Text(color = Color.White, text = "Cancelar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EdicionPreview() {
    MaterialTheme {
        PantallaEdicion(
            serie = SerieRepository.getSeries()[0],
            error = null,
            onVolver = {},
            onGuardar = { _, _, _ -> },
            onLimpiarError = {}
        )
    }
}
