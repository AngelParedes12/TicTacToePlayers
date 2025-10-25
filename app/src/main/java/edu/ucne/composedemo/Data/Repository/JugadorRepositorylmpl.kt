package edu.ucne.composedemo.Data.Repository

import edu.ucne.composedemo.Data.Local.Dao.JugadorDao
import edu.ucne.composedemo.Data.Mappers.toDomain
import edu.ucne.composedemo.Data.Mappers.toEntity
import edu.ucne.composedemo.Data.Mappers.toEntity as dtoToEntity
import edu.ucne.composedemo.Data.Remote.JugadorRemoteDataSource
import edu.ucne.composedemo.Data.Remote.dto.JugadorPostDto
import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import edu.ucne.composedemo.Util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class JugadorRepositoryImpl @Inject constructor(
    private val dao: JugadorDao,
    private val remote: JugadorRemoteDataSource
) : JugadorRepository {
    override fun observe(): Flow<List<Jugador>> =
        dao.observeAll().map { it.map { e -> e.toDomain() } }

    override suspend fun guardarLocal(
        nombres: String,
        apellidos: String?,
        telefono: String?,
        email: String?,
        partidas: Int
    ): Resource<Jugador> {
        val j = Jugador(
            id = UUID.randomUUID().toString(),
            remoteId = null,
            nombres = nombres,
            apellidos = apellidos,
            telefono = telefono,
            email = email,
            partidas = partidas,
            isPendingCreate = true
        )
        dao.upsert(j.toEntity())
        return Resource.Success(j)
    }

    override suspend fun postPendientes(): Resource<Unit> {
        val pend = dao.getPendingCreate()
        for (p in pend) {
            val r = remote.create(JugadorPostDto(nombres = p.nombres, email = p.email))
            if (r is Resource.Success) {
                val synced = p.copy(remoteId = r.data.jugadorId, isPendingCreate = false)
                dao.upsert(synced)
            } else return Resource.Error("Sync error")
        }
        return Resource.Success(Unit)
    }

    override suspend fun syncFull(): Resource<Unit> {
        val r = remote.getAll()
        return if (r is Resource.Success) {
            dao.clear()
            dao.upsertAll(r.data.map { it.dtoToEntity() })
            Resource.Success(Unit)
        } else Resource.Error("Sync error")
    }

    override suspend fun obtener(id: String): Jugador? = dao.getById(id)?.toDomain()

    override suspend fun eliminar(id: String): Resource<Unit> {
        dao.delete(id)
        return Resource.Success(Unit)
    }
}
