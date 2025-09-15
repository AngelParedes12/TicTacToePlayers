package edu.ucne.composedemo.Presentation.Jugador

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun JugadorScreen(
    jugadorId: Int?,
    viewModel: JugadorViewModel = hiltViewModel(),
    goback: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        jugadorId?.takeIf { it > 0 }?.let {
            viewModel.findJugador(it)
        }
    }

    JugadorBodyScreen(
        uiState = uiState,
        onAction = viewModel::onEvent,
        goback = goback,
        saveJugador = {
            scope.launch {
                if (viewModel.saveJugador()) goback()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadorBodyScreen(
    uiState: JugadorUiState,
    onAction: (JugadorEvent) -> Unit,
    goback: () -> Unit,
    saveJugador: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.jugadorId != null && uiState.jugadorId != 0)
                            "Editar Jugador"
                        else
                            "Nuevo Jugador"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goback) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.jugadorId?.toString() ?: "0",
                onValueChange = {},
                label = { Text("Id:") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.nombres,
                onValueChange = { onAction(JugadorEvent.NombreChange(it)) },
                label = { Text("Nombre:") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.partidas.toString(),
                onValueChange = {
                    val partidas = it.toIntOrNull() ?: 0
                    onAction(JugadorEvent.PartidaChange(partidas))
                },
                label = { Text("Partidas") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )

            uiState.errorMessage?.takeIf { it.isNotEmpty() }?.let {
                Text(text = it, color = colors.error)
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(onClick = { onAction(JugadorEvent.new) }) {
                    Text("Limpiar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Refresh, "Limpiar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(onClick = { scope.launch { saveJugador() } }) {
                    Text("Guardar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, "Guardar")
                }
            }
        }
    }
}