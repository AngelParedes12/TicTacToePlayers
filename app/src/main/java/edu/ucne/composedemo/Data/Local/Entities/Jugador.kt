package edu.ucne.composedemo.Data.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jugadores")

data class JugadorEntity(
    @PrimaryKey
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: Int = 0
)
