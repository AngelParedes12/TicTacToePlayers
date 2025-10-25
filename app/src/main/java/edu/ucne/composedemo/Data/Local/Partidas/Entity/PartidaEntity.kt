package edu.ucne.composedemo.Data.Local.Partidas.Entity

import androidx.room.*
import edu.ucne.composedemo.Data.Local.Jugador.Entities.JugadorEntity

@Entity(
    tableName = "Partidas",
    foreignKeys = [
        ForeignKey(
            entity = JugadorEntity::class,
            parentColumns = ["jugadorId"],
            childColumns = ["jugador1Id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = JugadorEntity::class,
            parentColumns = ["jugadorId"],
            childColumns = ["jugador2Id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = JugadorEntity::class,
            parentColumns = ["jugadorId"],
            childColumns = ["ganadorId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("jugador1Id"), Index("jugador2Id"), Index("ganadorId")]
)
data class PartidaEntity(
    @PrimaryKey(autoGenerate = true) val partidaId: Int = 0,
    val fecha: String,
    val jugador1Id: Int,
    val jugador2Id: Int,
    val ganadorId: Int?,
    val esFinalizada: Boolean
)
