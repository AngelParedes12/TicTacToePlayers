package edu.ucne.composedemo.Data.Remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartidaDto(
    @SerialName("partidaId") val partidaId: Int,
    @SerialName("fecha") val fecha: String? = null,
    @SerialName("jugador1Id") val jugador1Id: Int? = null,
    @SerialName("jugador2Id") val jugador2Id: Int? = null,
    @SerialName("ganadorId") val ganadorId: Int? = null,
    @SerialName("esFinalizada") val esFinalizada: Boolean? = null
)

@Serializable
data class PartidaPostDto(
    @SerialName("jugador1Id") val jugador1Id: Int,
    @SerialName("jugador2Id") val jugador2Id: Int
)
