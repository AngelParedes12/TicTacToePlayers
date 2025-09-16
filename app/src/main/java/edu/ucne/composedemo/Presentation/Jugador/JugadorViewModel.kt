package edu.ucne.composedemo.Presentation.Jugador

import androidx.lifecycle.ViewModel
import edu.ucne.composedemo.Domain.useCase.UseCaseJugador.JugadorUseCases
import edu.ucne.composedemo.Domain.Model.Jugador
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorViewModel @Inject constructor(
    private val useCases: JugadorUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(JugadorUiState.default())
    val uiState = _uiState.asStateFlow()

    init {
        getJugadores()
    }

    fun onEvent(event: JugadorEvent){
        when(event){
            is JugadorEvent.NombreChange -> onNombresChange(event.nombres)
            is JugadorEvent.JugadorChange -> onJugadorChange(event.jugadorId)
            is JugadorEvent.PartidaChange -> onPartidasChange(event.partidas)
            JugadorEvent.delete -> deleteJugador()
            JugadorEvent.new -> nuevo()
            JugadorEvent.save -> viewModelScope.launch { saveJugador() }
        }
    }

    private fun nuevo() {
        _uiState.value = JugadorUiState.default()
    }

    private fun getJugadores() {
        viewModelScope.launch {
            try {
                useCases.obtenerJugadores().collectLatest { jugadores ->
                    _uiState.update { it.copy(jugadores = jugadores) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }


    fun findJugador(jugadorId: Int) {
        viewModelScope.launch {
            if (jugadorId > 0) {
                try {
                    val jugador = useCases.obtenerJugador(jugadorId)
                    _uiState.update {
                        it.copy(
                            jugadorId = jugador?.id,
                            nombres = jugador?.nombre.orEmpty(),
                            partidas = jugador?.partidas ?: 0
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
                }
            }
        }
    }


    suspend fun saveJugador(): Boolean {
        val currentState = _uiState.value
        val jugador = Jugador(
            id = currentState.jugadorId,
            nombre = currentState.nombres,
            partidas = currentState.partidas
        )
        return try {
            useCases.guardarJugador(jugador)
            true
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            false
        }
    }


    private fun deleteJugador() {
        viewModelScope.launch {
            try {
                val jugador = Jugador(
                    id = _uiState.value.jugadorId,
                    nombre = _uiState.value.nombres,
                    partidas = _uiState.value.partidas
                )
                useCases.eliminarJugador(jugador)
                getJugadores()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }


    private fun onPartidasChange(partidas: Int){
        _uiState.update { it.copy(partidas = partidas) }
    }

    private fun onNombresChange(nombres: String){
        _uiState.update { it.copy(nombres = nombres) }
    }

    private fun onJugadorChange(jugadorId: Int){
        _uiState.update { it.copy(jugadorId = jugadorId) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}