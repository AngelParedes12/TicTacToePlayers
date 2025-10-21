package edu.ucne.composedemo.Presentation.Jugador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun JugadorScreen(
    partidaIdArg: Int? = null,
    viewModel: JugadorViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsStateWithLifecycle()

    LaunchedEffect(partidaIdArg) {
        partidaIdArg?.let { viewModel.setPartida(it) }
        viewModel.load()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        PartidaSelector(
            currentPartidaId = gameState.partidaId,
            onPartidaChange = { id ->
                viewModel.setPartida(id)
                viewModel.load()
            }
        )

        Spacer(Modifier.height(20.dp))

        TurnIndicator(turn = gameState.turn)

        Spacer(Modifier.height(16.dp))

        GameBoard(
            board = gameState.board,
            loading = gameState.loading,
            onCellClick = { row, column ->
                viewModel.play(row, column)
            }
        )

        gameState.error?.let { error ->
            Spacer(Modifier.height(20.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun PartidaSelector(
    currentPartidaId: Int,
    onPartidaChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by remember(currentPartidaId) {
            mutableStateOf(currentPartidaId.toString())
        }

        OutlinedTextField(
            value = text,
            onValueChange = { input ->
                text = input.filter { it.isDigit() }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            label = { Text("Número de partida") },
            shape = RoundedCornerShape(14.dp)
        )

        IconButton(
            onClick = {
                text.toIntOrNull()?.let { id ->
                    onPartidaChange(id)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Refrescar partida"
            )
        }
    }
}

@Composable
private fun TurnIndicator(turn: String) {
    Text(
        text = "Turno de: $turn",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun GameBoard(
    board: List<List<String>>,
    loading: Boolean,
    onCellClick: (Int, Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(3) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(3) { column ->
                    val cellValue = board[row][column]
                    BoardCell(
                        value = cellValue,
                        enabled = cellValue.isEmpty() && !loading,
                        onClick = { onCellClick(row, column) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    value: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(104.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}