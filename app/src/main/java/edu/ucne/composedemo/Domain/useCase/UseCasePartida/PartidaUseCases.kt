package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Model.PartidaDetalle
import edu.ucne.composedemo.Domain.Model.PartidaModel
import edu.ucne.composedemo.Domain.Repository.PartidaRepository
import kotlinx.coroutines.flow.Flow

class ListarPartidasUseCase(private val repo: PartidaRepository) {
    operator fun invoke(): Flow<List<PartidaDetalle>> = repo.getPartidasDetalle()
}

class ObtenerPartidaUseCase(private val repo: PartidaRepository) {
    suspend operator fun invoke(id: Int): PartidaModel? = repo.findById(id)
}

class GuardarPartidaUseCase(private val repo: PartidaRepository) {
    suspend operator fun invoke(model: PartidaModel): Result<Long> {
        if (model.fecha.isBlank()) return Result.failure(IllegalArgumentException("Fecha obligatoria"))
        if (model.jugador1Id == model.jugador2Id) return Result.failure(IllegalArgumentException("Los jugadores deben ser distintos"))
        if (!repo.jugadorExiste(model.jugador1Id) || !repo.jugadorExiste(model.jugador2Id))
            return Result.failure(IllegalArgumentException("Jugador inexistente"))
        if (model.ganadorId != null && model.ganadorId !in listOf(model.jugador1Id, model.jugador2Id))
            return Result.failure(IllegalArgumentException("Ganador debe ser Jugador 1 o 2"))

        return if (model.partidaId == 0)
            Result.success(repo.insert(model))
        else {
            repo.update(model)
            Result.success(model.partidaId.toLong())
        }
    }
}

class EliminarPartidaUseCase(private val repo: PartidaRepository) {
    suspend operator fun invoke(model: PartidaModel): Int = repo.delete(model)
}

data class PartidaUseCases(
    val listarPartidas: ListarPartidasUseCase,
    val obtenerPartida: ObtenerPartidaUseCase,
    val guardarPartida: GuardarPartidaUseCase,
    val eliminarPartida: EliminarPartidaUseCase
)
