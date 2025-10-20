package edu.ucne.composedemo.Presentation.Partidas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.Domain.Model.PartidaModel
import edu.ucne.composedemo.Domain.useCase.PartidaUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartidaViewModel @Inject constructor(
    private val use: PartidaUseCases
) : ViewModel() {

    data class UiState(
        val lista: List<edu.ucne.composedemo.Domain.Model.PartidaDetalle> = emptyList(),
        val partidaId: Int = 0,
        val fecha: String = "",
        val jugador1Id: String = "",
        val jugador2Id: String = "",
        val ganadorId: String = "",
        val esFinalizada: Boolean = false,
        val error: String? = null,
        val ok: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            use.listarPartidas().collect { list ->
                _state.value = _state.value.copy(lista = list)
            }
        }
    }

    fun nuevo() {
        _state.value = UiState(
            fecha = java.time.LocalDateTime.now().toString().substring(0,16)
        )
    }

    fun cargar(id: Int) {
        viewModelScope.launch {
            val p = use.obtenerPartida(id)
            p?.let {
                _state.value = _state.value.copy(
                    partidaId = it.partidaId,
                    fecha = it.fecha,
                    jugador1Id = it.jugador1Id.toString(),
                    jugador2Id = it.jugador2Id.toString(),
                    ganadorId = it.ganadorId?.toString() ?: "",
                    esFinalizada = it.esFinalizada,
                    error = null, ok = null
                )
            }
        }
    }

    fun setFecha(v: String) { _state.value = _state.value.copy(fecha = v) }
    fun setJ1(v: String)    { _state.value = _state.value.copy(jugador1Id = v) }
    fun setJ2(v: String)    { _state.value = _state.value.copy(jugador2Id = v) }
    fun setGanador(v: String){ _state.value = _state.value.copy(ganadorId = v) }
    fun setFinalizada(v: Boolean){ _state.value = _state.value.copy(esFinalizada = v) }

    fun guardar(onDone: () -> Unit) {
        viewModelScope.launch {
            val s = _state.value
            val model = PartidaModel(
                partidaId = s.partidaId,
                fecha = s.fecha.trim(),
                jugador1Id = s.jugador1Id.toIntOrNull() ?: -1,
                jugador2Id = s.jugador2Id.toIntOrNull() ?: -1,
                ganadorId = s.ganadorId.trim().ifEmpty { null }?.toIntOrNull(),
                esFinalizada = s.esFinalizada
            )
            val r = use.guardarPartida(model)
            _state.value = r.fold(
                onSuccess = { s.copy(ok = if (model.partidaId == 0) "Partida creada" else "Partida actualizada", error = null) },
                onFailure = { e -> s.copy(error = e.message ?: "Error", ok = null) }
            )
            if (r.isSuccess) onDone()
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            val p = use.obtenerPartida(id) ?: return@launch
            val rows = use.eliminarPartida(p)
            if (rows > 0) _state.value = _state.value.copy(ok = "Partida eliminada", error = null)
        }
    }
}
