package edu.ucne.composedemo.Data.Remote.Dto

import kotlinx.serialization.Serializable

@Serializable
data class PartidaDto(
    val partidaId: Int,
    val jugador1Id: Int,
    val jugador2Id: Int
)

@Serializable
data class PartidaPostDto(
    val jugador1Id: Int,
    val jugador2Id: Int
)
