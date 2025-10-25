package edu.ucne.composedemo.Data.Local.Jugador.Entities.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import edu.ucne.composedemo.Data.Local.Jugador.Entities.JugadorEntity


@Dao
interface JugadorDao{
    @Upsert
    suspend fun save(jugador: JugadorEntity)

    @Query("""
            SELECT *
            FROM Jugadores
            WHERE jugadorId =:id
            Limit 1
    """)
    suspend fun find(id: Int): JugadorEntity?

    @Delete
    suspend fun delete(jugador: JugadorEntity)

    @Query("SELECT * FROM Jugadores")
    fun getAll(): Flow<List<JugadorEntity>>
}