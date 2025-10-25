package edu.ucne.composedemo.Domain.Model

data class Jugador(
    val id: String,
    val remoteId: Long?,
    val nombres: String,
    val apellidos: String?,
    val telefono: String?,
    val email: String?,
    val partidas: Int,
    val isPendingCreate: Boolean
)
