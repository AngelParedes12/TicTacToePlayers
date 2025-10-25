package edu.ucne.composedemo.Data.Local.Jugador.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class JugadorEntity(
    @PrimaryKey val id: String,
    val remoteId: Long?,
    val nombres: String,
    val apellidos: String?,
    val telefono: String?,
    val email: String?,
    val partidas: Int,
    val isPendingCreate: Boolean
)
