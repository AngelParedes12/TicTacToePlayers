package edu.ucne.composedemo.Presentation.TicTacToe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.Data.Remote.TicTacToeApi
import edu.ucne.composedemo.Data.Remote.dto.MovimientoDto
import edu.ucne.composedemo.Domain.Repository.MovimientosRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class GameUiState(
    val partidaId: Int = 1,
    val board: List<List<String>> = List(3) { List(3) { "" } },
    val turn: String = "X",
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class GameViewModel @Inject constructor(
    private val movimientosRepo: MovimientosRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(GameUiState())
    val ui: StateFlow<GameUiState> = _ui

    fun setPartida(id: Int) {
        _ui.update { it.copy(partidaId = id) }
        load()
    }

    fun load() {
        viewModelScope.launch {
            try {
                _ui.update { it.copy(loading = true, error = null) }
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
                _ui.update { it.copy(board = b.map { r -> r.toList() }, turn = next, loading = false, error = null) }
            } catch (e: HttpException) {
                val msg = if (e.code() == 404) "Partida ${_ui.value.partidaId} no encontrada" else "HTTP ${e.code()} ${e.message()}"
                _ui.update { it.copy(loading = false, error = msg) }
            } catch (e: Exception) {
                _ui.update { it.copy(loading = false, error = e.message ?: "Error") }
            }
        }
    }

//    fun play(r: Int, c: Int) {
//        val symbol = _ui.value.turn
//        if (r !in 0..2 || c !in 0..2 || _ui.value.board[r][c].isNotEmpty()) return
//        viewModelScope.launch {
//            try {
//                _ui.update { it.copy(loading = true, error = null) }
//                movimientosRepo.post(MovimientoDto(_ui.value.partidaId, symbol, r, c))
//                load()
//            } catch (e: HttpException) {
//                val msg = if (e.code() == 404) "Partida ${_ui.value.partidaId} no encontrada" else "HTTP ${e.code()} ${e.message()}"
//                _ui.update { it.copy(loading = false, error = msg) }
//            } catch (e: Exception) {
//                _ui.update { it.copy(loading = false, error = e.message ?: "Error") }
//            }
//        }
//    }
}
