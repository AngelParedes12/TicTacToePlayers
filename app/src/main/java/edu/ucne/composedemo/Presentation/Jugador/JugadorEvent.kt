package edu.ucne.jugadorestictactoe.Presentation.Jugador


sealed class JugadorEvent {
    data class NombreChange(val nombres: String) : JugadorEvent()
    data class PartidaChange(val partidas: Int) : JugadorEvent()
    data class JugadorChange(val jugadorId: Int) : JugadorEvent()
    object delete : JugadorEvent()
    object new : JugadorEvent()
    object save : JugadorEvent()
}
