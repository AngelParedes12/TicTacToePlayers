package edu.ucne.composedemo.Data.Repository

import edu.ucne.composedemo.Data.Local.Jugador.Entities.Dao.JugadorDao
import edu.ucne.composedemo.Data.Local.Partidas.Dao.PartidaDao
import edu.ucne.composedemo.Data.Local.Partidas.Dto.PartidaDetalleRow
import edu.ucne.composedemo.Data.Local.Partidas.Entity.PartidaEntity
import edu.ucne.composedemo.Domain.Model.PartidaDetalle
import edu.ucne.composedemo.Domain.Model.PartidaModel
import edu.ucne.composedemo.Domain.Repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PartidaRepositoryImpl @Inject constructor(
    private val partidaDao: PartidaDao,
    private val jugadorDao: JugadorDao
) : PartidaRepository {

    override fun getPartidasDetalle(): Flow<List<PartidaDetalle>> =
        partidaDao.getPartidasDetalle().map { rows -> rows.map { it.toDomain() } }

    override suspend fun findById(id: Int): PartidaModel? =
        partidaDao.findById(id)?.toDomain()

    override suspend fun insert(model: PartidaModel): Long =
        partidaDao.insert(model.toEntity())

    override suspend fun update(model: PartidaModel): Int =
        partidaDao.update(model.toEntity())

    override suspend fun delete(model: PartidaModel): Int =
        partidaDao.delete(model.toEntity())

    override suspend fun jugadorExiste(id: Int): Boolean =
        jugadorDao.find(id) != null
}

private fun PartidaEntity.toDomain() = PartidaModel(
    partidaId = partidaId,
    fecha = fecha,
    jugador1Id = jugador1Id,
    jugador2Id = jugador2Id,
    ganadorId = ganadorId,
    esFinalizada = esFinalizada
)

private fun PartidaModel.toEntity() = PartidaEntity(
    partidaId = partidaId,
    fecha = fecha,
    jugador1Id = jugador1Id,
    jugador2Id = jugador2Id,
    ganadorId = ganadorId,
    esFinalizada = esFinalizada
)

private fun PartidaDetalleRow.toDomain() = PartidaDetalle(
    partidaId = partidaId,
    fecha = fecha,
    jugador1Id = jugador1Id,
    jugador1Nombre = jugador1Nombre,
    jugador2Id = jugador2Id,
    jugador2Nombre = jugador2Nombre,
    ganadorId = ganadorId,
    ganadorNombre = ganadorNombre,
    esFinalizada = esFinalizada
)
