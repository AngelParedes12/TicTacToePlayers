package edu.ucne.composedemo.Presentation.Jugador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.Domain.Model.Jugador

@Composable
fun JugadorScreen(
    viewModel: JugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        val m = state.message
        if (m != null) {
            snackbar.showSnackbar(m)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        JugadorBody(
            paddingValues = padding,
            state = state,
            onNameChange = { viewModel.onEvent(JugadorEvent.NombresChanged(it)) },
            onEmailChange = { viewModel.onEvent(JugadorEvent.EmailChanged(it)) },
            onGuardar = { viewModel.onEvent(JugadorEvent.Submit) },
            onSync = { viewModel.onEvent(JugadorEvent.Sync) },
            loading = state.isLoading
        )
    }
}

@Composable
private fun JugadorBody(
    paddingValues: PaddingValues,
    state: JugadorUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onSync: () -> Unit,
    loading: Boolean
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.nombres,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onGuardar,
                    enabled = state.nombres.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar local y sync")
                }
                Button(
                    onClick = onSync,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sync ahora")
                }
            }

            if (loading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        Divider()

        Text("Jugadores registrados:")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.jugadores) { jugador ->
                JugadorRow(jugador = jugador)
            }
        }
    }
}

@Composable
private fun JugadorRow(
    jugador: Jugador
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Remoto: ${jugador.remoteId ?: "-"}")
        Text("Nombre: ${jugador.nombres}")
        Text("Email: ${jugador.email ?: ""}")
    }
}

