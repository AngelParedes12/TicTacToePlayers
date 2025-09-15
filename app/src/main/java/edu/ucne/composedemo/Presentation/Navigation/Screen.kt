package edu.ucne.composedemo.Presentation.Navigation

import kotlinx.serialization.Serializable
@Serializable
sealed class Screen{
    @Serializable
    data class Jugador(val Id: Int?): Screen()

    @Serializable
    data object List: Screen()
}