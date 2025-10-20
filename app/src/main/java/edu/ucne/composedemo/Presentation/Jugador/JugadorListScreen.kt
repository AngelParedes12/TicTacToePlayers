package edu.ucne.jugadorestictactoe.Presentation.Jugador


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.Domain.Model.Jugador
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column

@Composable
fun JugadorListScreen(
    viewModel: JugadorViewModel = hiltViewModel(),
    goToJugadores: (Int) -> Unit,
    createJugador: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.takeIf { it.isNotEmpty() }?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }

    val onDelete: (Jugador) -> Unit = { jugador ->
        viewModel.onEvent(JugadorEvent.JugadorChange(jugador.id ?: 0))
        viewModel.onEvent(JugadorEvent.delete)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = createJugador) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar jugador")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(state.jugadores) { jugador ->
                JugadorCardItem(
                    jugador = jugador,
                    goToJugador = { goToJugadores(jugador.id ?: 0) },
                    deleteJugador = { onDelete(jugador) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun JugadorCardItem(
    jugador: Jugador,
    goToJugador: () -> Unit,
    deleteJugador: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "Id: ${jugador.id}", fontWeight = FontWeight.Bold)
                Text(text = jugador.nombre, fontSize = 14.sp)
                Text(text = "${jugador.partidas} Partidas jugadas")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = goToJugador) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = deleteJugador) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
