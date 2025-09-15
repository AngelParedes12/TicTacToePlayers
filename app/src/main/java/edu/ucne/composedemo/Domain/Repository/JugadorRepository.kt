package edu.ucne.composedemo.Domain.Repository

import edu.ucne.composedemo.Domain.Model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    suspend fun save(jugador: Jugador): Boolean
    suspend fun find(id: Int): Jugador?
    suspend fun delete(jugador: Jugador)
    suspend fun getAll(): List<Jugador>
    fun getAllFlow(): Flow<List<Jugador>>
}