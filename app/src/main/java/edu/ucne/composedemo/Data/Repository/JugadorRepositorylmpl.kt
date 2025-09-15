package edu.ucne.composedemo.Data.Repository

import edu.ucne.composedemo.Data.Local.Dao.JugadorDao
import edu.ucne.composedemo.Data.Local.Entities.JugadorEntity
import edu.ucne.composedemo.Data.mappers.toDomain
import edu.ucne.composedemo.Data.mappers.toEntity
import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class JugadorRepositorylmpl @Inject constructor(
    private val dao: JugadorDao
) : JugadorRepository {

    override suspend fun save(jugador: Jugador): Boolean {
        dao.save(jugador.toEntity())
        return true
    }

    override suspend fun find(id: Int): Jugador? =
        dao.find(id)?.toDomain()

    override suspend fun delete(jugador: Jugador) {
        dao.delete(jugador.toEntity())
    }

    override suspend fun getAll(): List<Jugador> =
        dao.getAll().firstOrNull()?.map { it.toDomain() } ?: emptyList()

    override fun getAllFlow(): Flow<List<Jugador>> =
        dao.getAll().map { entities -> entities.map(JugadorEntity::toDomain) }

}