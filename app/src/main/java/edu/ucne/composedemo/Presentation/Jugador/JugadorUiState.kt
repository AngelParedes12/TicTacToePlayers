package edu.ucne.composedemo.Presentation.Jugador

import edu.ucne.composedemo.Domain.Model.Jugador

data class JugadorUiState (
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: Int = 0,
    val errorMessage: String? = null,
    val jugadores: List<Jugador> = emptyList()
) {
    companion object {
        fun default() = JugadorUiState()
    }
}
