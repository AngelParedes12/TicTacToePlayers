package edu.ucne.composedemo.Data.Local.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.Data.Local.Entities.JugadorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao {
    @Query("SELECT * FROM jugadores ORDER BY rowid DESC")
    fun observeAll(): Flow<List<JugadorEntity>>

    @Query("SELECT * FROM jugadores WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): JugadorEntity?

    @Upsert
    suspend fun upsert(entity: JugadorEntity)

    @Upsert
    suspend fun upsertAll(list: List<JugadorEntity>)

    @Query("DELETE FROM jugadores WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM jugadores")
    suspend fun clear()

    @Query("SELECT * FROM jugadores WHERE isPendingCreate = 1")
    suspend fun getPendingCreate(): List<JugadorEntity>
}
