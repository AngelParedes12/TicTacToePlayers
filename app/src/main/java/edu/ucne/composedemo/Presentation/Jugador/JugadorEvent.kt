package edu.ucne.composedemo.Presentation.Jugador
sealed interface JugadorEvent {
    data class JugadorChange(val jugadorId: Int) : JugadorEvent
    data class NombreChange(val nombres: String) : JugadorEvent
    data class PartidaChange(val partidas: Int): JugadorEvent
    data object save: JugadorEvent
    data object delete: JugadorEvent
    data object new: JugadorEvent
}