package edu.ucne.composedemo.Data.Repository

import edu.ucne.composedemo.Data.Remote.TicTacToeApi
import edu.ucne.composedemo.Data.Remote.dto.MovimientoDto
import edu.ucne.composedemo.Data.Remote.dto.MovimientoPostDto
import edu.ucne.composedemo.Domain.Repository.MovimientosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovimientosRepositoryImpl @Inject constructor(
    private val api: TicTacToeApi
) : MovimientosRepository {

    override suspend fun getByPartida(partidaId: Int): List<MovimientoDto> {
        return api.getMovimientos(partidaId)
    }

    override suspend fun post(move: MovimientoPostDto) {
        val r = api.postMovimiento(move)
        if (!r.isSuccessful) throw IllegalStateException("HTTP ${r.code()} ${r.message()}")
    }
}
