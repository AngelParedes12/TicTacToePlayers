package edu.ucne.composedemo.Presentation.Jugador

sealed interface JugadorEvent {
    data class JugadorChange(val id: Int) : JugadorEvent
    data object Delete : JugadorEvent
}
