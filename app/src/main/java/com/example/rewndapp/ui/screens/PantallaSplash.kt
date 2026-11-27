package com.example.rewndapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rewndapp.ui.theme.FondoSplash
import kotlinx.coroutines.delay
import com.example.rewndapp.R

@Composable
fun PantallaSplash(
    onTerminar: () -> Unit
){
    var cargando by remember { mutableStateOf(false) }

    // la barra pasa de 0 a 1 en 2 segundos
    val progreso by animateFloatAsState(
        targetValue = if (cargando) 1f else 0f,
        animationSpec = tween(durationMillis = 2000),
        label = "progreso"
    )

    LaunchedEffect(Unit) {
        cargando = true
        delay(2000)
        onTerminar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoSplash),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_rewnd),
            contentDescription = "Logo de Rewnd",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Comenta sin spoilers",
            color = Color(0xFFE0D2C8),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(48.dp))

        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp),
            color = Color.Black,
            trackColor = Color.White
        )
    }
}

@Preview(showBackground = true, widthDp = 411, heightDp = 850)
@Composable
fun SplashPreview() {
    MaterialTheme {
        PantallaSplash(onTerminar = {})
    }
}
