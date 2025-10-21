package edu.ucne.composedemo.Domain.Repository

import edu.ucne.composedemo.Data.Remote.dto.MovimientoDto
import edu.ucne.composedemo.Data.Remote.dto.MovimientoPostDto

interface MovimientosRepository {
    suspend fun getByPartida(partidaId: Int): List<MovimientoDto>
    suspend fun post(move: MovimientoPostDto)
}
