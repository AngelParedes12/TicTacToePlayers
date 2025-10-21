package edu.ucne.composedemo.Data.Remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovimientoDto(
    @SerialName("jugador") val jugador: String,
    @SerialName("posicionFila") val posicionFila: Int,
    @SerialName("posicionColumna") val posicionColumna: Int
)

@Serializable
data class MovimientoPostDto(
    @SerialName("partidaId") val partidaId: Int,
    @SerialName("jugador") val jugador: String,
    @SerialName("posicionFila") val posicionFila: Int,
    @SerialName("posicionColumna") val posicionColumna: Int
)
