package com.example.rewndapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rewndapp.ui.theme.Fondo
import com.example.rewndapp.ui.theme.Naranja
import com.example.rewndapp.R
import com.example.rewndapp.ui.theme.FondoCaratula
import com.example.rewndapp.ui.theme.TextoSecundario

@Composable
fun PantallaLogin(
    cargando: Boolean,
    error: String?,
    onLogin: (String, String) -> Unit,
    onIrARegistro: () -> Unit,
    onIrARecuperar: () -> Unit,
    onLimpiarError: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var verContrasenia by remember { mutableStateOf(false) }

    LaunchedEffect(email, contrasenia) {
        onLimpiarError()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(Naranja)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_rewnd),
                contentDescription = "Logo de Rewnd",
                modifier = Modifier.size(140.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Inicia sesión en tu cuenta",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Introduce tu correo electrónico y tu contraseña para iniciar sesión",
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 400.dp)
                    .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                    .background(Fondo, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Correo electrónico", color = Color.White, fontSize = 12.sp)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Text("Contraseña", color = Color.White, fontSize = 12.sp)
                OutlinedTextField(
                    value = contrasenia,
                    onValueChange = { contrasenia = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (verContrasenia) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { verContrasenia = !verContrasenia }) {
                            Icon(
                                painter = painterResource(
                                    id = if (verContrasenia) R.drawable.ic_ojo_tachado
                                    else R.drawable.ic_ojo
                                ),
                                contentDescription = "Mostrar u ocultar contraseña",
                                tint = Color.Gray
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Text(
                    "¿Olvidaste tu contraseña?",
                    color = Naranja,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                        .clickable { onIrARecuperar() }
                )

                if (error != null) {
                    Text(error, color = Color(0xFFFF6B6B), fontSize = 14.sp)
                }

                OutlinedButton(
                    onClick = { onLogin(email, contrasenia) },
                    enabled = !cargando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Naranja,
                        disabledContainerColor = FondoCaratula
                    )
                ) {
                    if (cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White
                        )
                    } else {
                        Text(color = Color.Black, text = "Iniciar Sesión")
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("¿No tienes cuenta? ", color = TextoSecundario, fontSize = 14.sp)
                    Text(
                        "Registrarse",
                        color = Naranja,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { onIrARegistro() }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MaterialTheme {
        PantallaLogin(
            cargando = false,
            error = null,
            onLogin = { _, _ -> },
            onIrARegistro = {},
            onIrARecuperar = {},
            onLimpiarError = {}
        )
    }
}