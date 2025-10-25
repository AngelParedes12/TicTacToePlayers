package edu.ucne.composedemo.Presentation.Partidas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidaListScreen(
    onNueva: () -> Unit,
    onEditar: (Int) -> Unit,
    vm: PartidaViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Partidas") }) },
        floatingActionButton = { FloatingActionButton(onClick = {
            vm.nuevo(); onNueva()
        }) { Text("+") } }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            state.ok?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.lista) { p ->
                    Surface(
                        tonalElevation = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEditar(p.partidaId) }
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("ID ${p.partidaId}  •  ${p.fecha}")
                            Text("J1: ${p.jugador1Nombre ?: p.jugador1Id} vs J2: ${p.jugador2Nombre ?: p.jugador2Id}")
                            Text("Ganador: ${p.ganadorNombre ?: (p.ganadorId ?: "—")}  •  Finalizada: ${p.esFinalizada}")
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                TextButton(onClick = { onEditar(p.partidaId) }) { Text("Editar") }
                                Spacer(Modifier.width(8.dp))
                                TextButton(onClick = { vm.eliminar(p.partidaId) }) { Text("Eliminar") }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidaFormScreen(
    partidaId: Int?,
    onBack: () -> Unit,
    vm: PartidaViewModel = hiltViewModel()
) {
    val id = partidaId ?: 0
    val state by vm.state.collectAsState()

    LaunchedEffect(id) {
        if (id == 0) vm.nuevo() else vm.cargar(id)
    }

    Scaffold(topBar = { TopAppBar(title = { Text(if (id == 0) "Nueva Partida" else "Editar Partida") }) }) { inner ->
        Column(
            Modifier.padding(inner).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.fecha, onValueChange = vm::setFecha,
                label = { Text("Fecha (ISO)") }, singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.jugador1Id, onValueChange = vm::setJ1,
                label = { Text("Jugador 1 (ID)") }, singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.jugador2Id, onValueChange = vm::setJ2,
                label = { Text("Jugador 2 (ID)") }, singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.ganadorId, onValueChange = vm::setGanador,
                label = { Text("Ganador (ID) opcional") }, singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = state.esFinalizada, onCheckedChange = vm::setFinalizada)
                Spacer(Modifier.width(8.dp)); Text("Finalizada")
            }

            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            state.ok?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onBack) { Text("Cancelar") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { vm.guardar(onBack) }) { Text("Guardar") }
            }
        }
    }
}
