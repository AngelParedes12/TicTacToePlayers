package edu.ucne.composedemo.Domain.Model

data class PartidaDetalle(
    val partidaId: Int,
    val fecha: String,
    val jugador1Id: Int,
    val jugador1Nombre: String?,
    val jugador2Id: Int,
    val jugador2Nombre: String?,
    val ganadorId: Int?,
    val ganadorNombre: String?,
    val esFinalizada: Boolean
)
