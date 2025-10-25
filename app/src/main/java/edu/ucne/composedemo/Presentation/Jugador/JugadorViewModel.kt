package edu.ucne.composedemo.Presentation.Jugador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.useCase.JugadorUseCases
import edu.ucne.composedemo.Util.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JugadorUiState(
    val jugadores: List<Jugador> = emptyList(),
    val nombres: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val message: String? = null
)

@HiltViewModel
class JugadorViewModel @Inject constructor(
    private val use: JugadorUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(JugadorUiState())
    val state: StateFlow<JugadorUiState> = _state

    init {
        viewModelScope.launch {
            use.obtenerTodos().collectLatest { list ->
                _state.update { it.copy(jugadores = list) }
            }
        }
    }

    fun onEvent(e: JugadorEvent) {
        when (e) {
            is JugadorEvent.NombresChanged -> _state.update { it.copy(nombres = e.v) }
            is JugadorEvent.EmailChanged -> _state.update { it.copy(email = e.v) }
            is JugadorEvent.Submit -> submit()
            is JugadorEvent.Sync -> triggerSync()
            is JugadorEvent.ApellidosChanged -> {}
            is JugadorEvent.TelefonoChanged -> {}
            is JugadorEvent.PartidasChanged -> {}
        }
    }

    private fun submit() = viewModelScope.launch {
        val s = state.value
        _state.update { it.copy(isLoading = true, message = null) }

        when (val r = use.guardarLocal(s.nombres, s.email.ifBlank { null })) {
            is Resource.Success -> {
                _state.update {
                    it.copy(
                        nombres = "",
                        email = "",
                        isLoading = false,
                        message = "Guardado local"
                    )
                }
            }
            is Resource.Error -> {
                _state.update { it.copy(isLoading = false, message = r.message) }
            }
            else -> {
                _state.update { it.copy(isLoading = false) }
            }
        }

        triggerSync()
    }

    private fun triggerSync() = viewModelScope.launch {
        use.postPendientes()
        use.syncFull()
    }
}
