package edu.ucne.composedemo.Domain.Model

data class PartidaModel(
    val partidaId: Int = 0,
    val fecha: String,
    val jugador1Id: Int,
    val jugador2Id: Int,
    val ganadorId: Int?,
    val esFinalizada: Boolean
)
