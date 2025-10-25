package edu.ucne.composedemo.Domain.Repository

import edu.ucne.composedemo.Domain.Model.PartidaDetalle
import edu.ucne.composedemo.Domain.Model.PartidaModel
import kotlinx.coroutines.flow.Flow

interface PartidaRepository {
    fun getPartidasDetalle(): Flow<List<PartidaDetalle>>
    suspend fun findById(id: Int): PartidaModel?
    suspend fun insert(model: PartidaModel): Long
    suspend fun update(model: PartidaModel): Int
    suspend fun delete(model: PartidaModel): Int
    suspend fun jugadorExiste(id: Int): Boolean
}
