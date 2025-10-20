package edu.ucne.composedemo.Data.Local.Partidas.Dao

import androidx.room.*
import edu.ucne.composedemo.Data.Local.Partidas.Dto.PartidaDetalleRow
import edu.ucne.composedemo.Data.Local.Partidas.Entity.PartidaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao {

    @Query("""
        SELECT p.partidaId, p.fecha,
               p.jugador1Id, j1.nombres AS jugador1Nombre,
               p.jugador2Id, j2.nombres AS jugador2Nombre,
               p.ganadorId,  g.nombres  AS ganadorNombre,
               p.esFinalizada
        FROM Partidas p
        LEFT JOIN Jugadores j1 ON j1.jugadorId = p.jugador1Id
        LEFT JOIN Jugadores j2 ON j2.jugadorId = p.jugador2Id
        LEFT JOIN Jugadores g  ON g.jugadorId  = p.ganadorId
        ORDER BY p.fecha DESC, p.partidaId DESC
    """)
    fun getPartidasDetalle(): Flow<List<PartidaDetalleRow>>

    @Query("SELECT * FROM Partidas WHERE partidaId = :id LIMIT 1")
    suspend fun findById(id: Int): PartidaEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: PartidaEntity): Long

    @Update
    suspend fun update(entity: PartidaEntity): Int

    @Delete
    suspend fun delete(entity: PartidaEntity): Int
}
