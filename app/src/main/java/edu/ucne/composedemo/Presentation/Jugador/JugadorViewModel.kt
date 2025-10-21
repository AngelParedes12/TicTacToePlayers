package edu.ucne.composedemo.Presentation.Jugador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.Data.Remote.dto.MovimientoPostDto
import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.MovimientosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class JugadorUiState(
    val jugadores: List<Jugador> = emptyList(),
    val errorMessage: String? = null
)

data class GameUiState(
    val partidaId: Int = 1,
    val board: List<List<String>> = List(3) { List(3) { "" } },
    val turn: String = "X",
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class JugadorViewModel @Inject constructor(
    private val movimientosRepo: MovimientosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JugadorUiState())
    val uiState: StateFlow<JugadorUiState> = _uiState

    private val _gameState = MutableStateFlow(GameUiState())
    val gameState: StateFlow<GameUiState> = _gameState

    fun setPartida(id: Int) {
        _gameState.value = _gameState.value.copy(partidaId = id)
    }

    fun clearError() {
        _gameState.value = _gameState.value.copy(error = null)
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun load() {
        viewModelScope.launch {
            try {
                _gameState.value = _gameState.value.copy(
                    loading = true,
                    error = null
                )

                val moves = movimientosRepo.getByPartida(_gameState.value.partidaId)
                val board = List(3) { MutableList(3) { "" } }
                var lastPlayer = ""

                for (move in moves) {
                    if (move.posicionFila in 0..2 && move.posicionColumna in 0..2) {
                        board[move.posicionFila][move.posicionColumna] = move.jugador
                        lastPlayer = move.jugador
                    }
                }

                val nextTurn = if (lastPlayer == "X") "O" else "X"

                _gameState.value = _gameState.value.copy(
                    board = board.map { it.toList() },
                    turn = nextTurn,
                    loading = false,
                    error = null
                )
            } catch (e: HttpException) {
                val errorMessage = if (e.code() == 404) {
                    "Partida ${_gameState.value.partidaId} no encontrada"
                } else {
                    "HTTP ${e.code()} ${e.message()}"
                }
                _gameState.value = _gameState.value.copy(
                    loading = false,
                    error = errorMessage
                )
            } catch (e: Exception) {
                _gameState.value = _gameState.value.copy(
                    loading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun play(row: Int, column: Int) {
        val symbol = _gameState.value.turn
        val currentBoard = _gameState.value.board

        if (row !in 0..2 || column !in 0..2 || currentBoard[row][column].isNotEmpty()) {
            return
        }

        viewModelScope.launch {
            try {
                _gameState.value = _gameState.value.copy(
                    loading = true,
                    error = null
                )

                movimientosRepo.post(
                    MovimientoPostDto(
                        partidaId = _gameState.value.partidaId,
                        jugador = symbol,
                        posicionFila = row,
                        posicionColumna = column
                    )
                )
                load()
            } catch (e: HttpException) {
                val errorMessage = if (e.code() == 404) {
                    "Partida ${_gameState.value.partidaId} no encontrada"
                } else {
                    "HTTP ${e.code()} ${e.message()}"
                }
                _gameState.value = _gameState.value.copy(
                    loading = false,
                    error = errorMessage
                )
            } catch (e: Exception) {
                _gameState.value = _gameState.value.copy(
                    loading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun onEvent(event: JugadorEvent) {
        when (event) {
            is JugadorEvent.JugadorChange -> { /* Implementar lógica */ }
            JugadorEvent.Delete -> { /* Implementar lógica */ }

        }
    }
}