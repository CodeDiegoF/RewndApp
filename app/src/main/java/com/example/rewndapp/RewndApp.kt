package com.example.rewndapp

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rewndapp.routes.Rutas
import com.example.rewndapp.ui.screens.PantallaAdicion
import com.example.rewndapp.ui.screens.PantallaBiblioteca
import com.example.rewndapp.ui.screens.PantallaBorrado
import com.example.rewndapp.ui.screens.PantallaDetalle
import com.example.rewndapp.ui.screens.PantallaEdicion
import com.example.rewndapp.ui.screens.PantallaLogin
import com.example.rewndapp.ui.screens.PantallaRecuperar
import com.example.rewndapp.ui.screens.PantallaRegistro
import com.example.rewndapp.ui.screens.PantallaSplash
import com.example.rewndapp.viewmodel.AuthViewModel
import com.example.rewndapp.viewmodel.SeriesViewModel

@Composable
fun RewndApp(viewModel: SeriesViewModel = viewModel(),
             authViewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()

    val uiState = viewModel.uiState
    val authUiState = authViewModel.uiState


    NavHost(
        navController = navController,
        startDestination = Rutas.SPLASH,
        modifier = Modifier.safeDrawingPadding(),

        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },

        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        }
    ){
        composable ( Rutas.SPLASH ){
            PantallaSplash{
                navController.navigate(Rutas.REGISTRO)
            }
        }

        composable(Rutas.REGISTRO) {
            PantallaRegistro(
                cargando = authUiState.cargando,
                error = authUiState.error,
                onRegistro = { email, contrasenia, repetir ->
                    authViewModel.onRegistro(email, contrasenia, repetir)
                },
                onIrALogin = { navController.navigate(Rutas.LOGIN) },
                onLimpiarError = { authViewModel.onLimpiarError() }
            )
        }

        composable (Rutas.LOGIN){
            PantallaLogin(
                cargando = authUiState.cargando,
                error = authUiState.error,
                onLogin = { email, contrasenia ->
                    authViewModel.onLogin(email, contrasenia)
                },
                onIrARegistro = { navController.navigate(Rutas.REGISTRO) },
                onIrARecuperar = {navController.navigate(Rutas.RECUPERAR_CLAVE)},
                onLimpiarError = { authViewModel.onLimpiarError() }
            )
        }

        composable (Rutas.RECUPERAR_CLAVE){
            PantallaRecuperar(
                cargando = authUiState.cargando,
                error = authUiState.error,
                mensaje = authUiState.mensaje,
                onRecuperar = {email -> authViewModel.onRecuperarClave(email)},
                onIrALogin = { navController.navigate(Rutas.LOGIN) },
                onLimpiarError = { authViewModel.onLimpiarError() }
            )
        }

        composable(Rutas.BIBLIOTECA) {
            PantallaBiblioteca(
                textoFiltro = uiState.textoFiltro,
                resultados = uiState.resultados,
                onBuscar = { texto -> viewModel.onFiltrarTexto(texto) },
                series = uiState.seriesFiltradas,
                onSerieClick = { serie -> navController.navigate(Rutas.detalle(serie.id)) },
                onAnadir = { navController.navigate(Rutas.ANADIR) },
                onFiltrar = { estado -> viewModel.onFiltrarSeries(estado) },
                filtroEstado = uiState.estadoSerie
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val serie = uiState.seriesList.find { it.id == id }
            if (serie != null){
                PantallaDetalle(
                    serie = serie,
                    onVolver = { navController.popBackStack() },
                    onEliminar = { navController.navigate(Rutas.eliminar(serie.id)) },
                    onEditar = {
                        viewModel.onLimpiarError()
                        navController.navigate(Rutas.editar(serie.id))
                    }
                )
            }
        }

        composable(Rutas.ANADIR) {
            PantallaAdicion(
                serieEncontrada = uiState.serieEncontrada,
                textoBusqueda = uiState.textoBusqueda,
                resultados = uiState.resultados,
                onBuscar = { texto -> viewModel.onBusqueda(texto) },
                onSeleccionar = { serie -> viewModel.onResultadoClick(serie) },
                onAnadir = {
                    if (uiState.serieEncontrada != null) {
                        viewModel.onConfirmarAnadir()
                        navController.popBackStack()
                    }
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = Rutas.ELIMINAR,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val serie = uiState.seriesList.find { it.id == id }
            if (serie != null){
                PantallaBorrado(
                    serie = serie,
                    onConfirmar = {
                        viewModel.onConfirmarBorrado(serie.id)
                        navController.popBackStack(route = Rutas.BIBLIOTECA, inclusive = false)
                    },
                    onCancelar = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = Rutas.EDITAR,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val serie = uiState.seriesList.find { it.id == id }
            if (serie != null) {
                PantallaEdicion(
                    serie = serie,
                    error = uiState.errorEdicion,
                    onVolver = { navController.popBackStack() },
                    onGuardar = { estado, temporada, terminada ->
                        val guardado = viewModel.onGuardarEdicion(estado, temporada, terminada, serie.id)
                        if (guardado) {
                            navController.popBackStack()
                        }
                    },
                    onLimpiarError = { viewModel.onLimpiarError() }
                )
            }
        }
    }
}