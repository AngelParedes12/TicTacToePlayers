package edu.ucne.composedemo.Presentation.Partida

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidasListScreen(
    goToPartida: (Int) -> Unit,
    vm: PartidasViewModel = hiltViewModel()
) {
    val state = vm.ui.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) { vm.load() }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Partidas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.crear { goToPartida(it) } }) {
                Icon(Icons.Filled.Add, contentDescription = "Crear partida")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.partidas.isEmpty() && !state.loading && state.error == null) {
                item {
                    Text(
                        "No hay partidas",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            items(state.partidas) { partida ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .clickable { goToPartida(partida.partidaId) },
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    ListItem(
                        headlineContent = { Text("Partida ${partida.partidaId}") }
                    )
                }
            }
            if (state.error != null) {
                item {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
