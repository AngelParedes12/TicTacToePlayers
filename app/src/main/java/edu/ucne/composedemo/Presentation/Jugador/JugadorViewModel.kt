package edu.ucne.composedemo.Presentation.Jugador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.Data.Remote.dto.MovimientoPostDto
import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.MovimientosRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

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

    private val _ui = MutableStateFlow(GameUiState())
    val ui: StateFlow<GameUiState> = _ui

    fun setPartida(id: Int) {
        _ui.value = _ui.value.copy(partidaId = id)
    }

    fun clearError() {
        _ui.value = _ui.value.copy(error = null)
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun load() {
        viewModelScope.launch {
            try {
                _ui.value = _ui.value.copy(loading = true, error = null)
                val moves = movimientosRepo.getByPartida(_ui.value.partidaId)
                val b = List(3) { MutableList(3) { "" } }
                var last = ""
                for (m in moves) {
                    if (m.posicionFila in 0..2 && m.posicionColumna in 0..2) {
                        b[m.posicionFila][m.posicionColumna] = m.jugador
                        last = m.jugador
                    }
                }
                val next = if (last == "X") "O" else "X"
                _ui.value = _ui.value.copy(
                    board = b.map { it.toList() },
                    turn = next,
                    loading = false,
                    error = null
                )
            } catch (e: HttpException) {
                val msg = if (e.code() == 404) "Partida ${_ui.value.partidaId} no encontrada" else "HTTP ${e.code()} ${e.message()}"
                _ui.value = _ui.value.copy(loading = false, error = msg)
            } catch (e: Exception) {
                _ui.value = _ui.value.copy(loading = false, error = e.message ?: "Error")
            }
        }
    }

    fun play(r: Int, c: Int) {
        val symbol = _ui.value.turn
        if (r !in 0..2 || c !in 0..2 || _ui.value.board[r][c].isNotEmpty()) return
        viewModelScope.launch {
            try {
                _ui.value = _ui.value.copy(loading = true, error = null)
                movimientosRepo.post(MovimientoPostDto(_ui.value.partidaId, symbol, r, c))
                load()
            } catch (e: HttpException) {
                val msg = if (e.code() == 404) "Partida ${_ui.value.partidaId} no encontrada" else "HTTP ${e.code()} ${e.message()}"
                _ui.value = _ui.value.copy(loading = false, error = msg)
            } catch (e: Exception) {
                _ui.value = _ui.value.copy(loading = false, error = e.message ?: "Error")
            }
        }
    }

    fun onEvent(event: JugadorEvent) {
        when (event) {
            is JugadorEvent.JugadorChange -> {}
            JugadorEvent.Delete -> {}
        }
    }
}
