package edu.ucne.composedemo.Presentation.Navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    // Jugadores
    @Serializable data object List : Screen()
    @Serializable data class Jugador(val Id: Int?) : Screen()

    // Partidas
    @Serializable data object Partidas : Screen()
    @Serializable data class Partida(val id: Int?) : Screen()
}
