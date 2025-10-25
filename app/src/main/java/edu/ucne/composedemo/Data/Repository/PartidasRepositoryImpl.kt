package edu.ucne.composedemo.Data.Repository

import edu.ucne.composedemo.Data.Remote.TicTacToeApi
import edu.ucne.composedemo.Data.Remote.Dto.PartidaDto
import edu.ucne.composedemo.Data.Remote.Dto.PartidaPostDto
import edu.ucne.composedemo.Domain.Repository.PartidasRepository
import javax.inject.Inject
import retrofit2.HttpException

class PartidasRepositoryImpl @Inject constructor(
    private val api: TicTacToeApi
) : PartidasRepository {
    override suspend fun exists(id: Int): Boolean {
        return try {
            api.getPartida(id)
            true
        } catch (e: HttpException) {
            if (e.code() == 404) false else throw e
        }
    }

    override suspend fun create(jugador1Id: Int, jugador2Id: Int): PartidaDto {
        return api.crearPartida(PartidaPostDto(jugador1Id, jugador2Id))
    }

    override suspend fun get(id: Int): PartidaDto = api.getPartida(id)
}
