package edu.ucne.jugadorestictactoe.Presentation.Jugador

import edu.ucne.composedemo.Domain.Model.Jugador

data class JugadorUiState(
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: Int = 0,
    val jugadores: List<Jugador> = emptyList(),
    val errorMessage: String? = null
) {
    companion object {
        fun default() = JugadorUiState()
    }
}
