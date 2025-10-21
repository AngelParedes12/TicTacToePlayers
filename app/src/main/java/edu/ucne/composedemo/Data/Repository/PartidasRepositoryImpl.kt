package edu.ucne.composedemo.Data.Repository

import edu.ucne.composedemo.Data.Remote.TicTacToeApi
import edu.ucne.composedemo.Data.Remote.dto.PartidaDto
import edu.ucne.composedemo.Data.Remote.dto.PartidaPostDto
import edu.ucne.composedemo.Domain.Repository.PartidasRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartidasRepositoryImpl @Inject constructor(
    private val api: TicTacToeApi
) : PartidasRepository {

    override suspend fun exists(id: Int): Boolean = try {
        api.getPartida(id)
        true
    } catch (e: retrofit2.HttpException) {
        if (e.code() == 404) false else throw e
    }

    override suspend fun get(id: Int): PartidaDto = api.getPartida(id)

    override suspend fun list(): List<PartidaDto> = api.getPartidas()

    override suspend fun create(jugador1Id: Int, jugador2Id: Int): PartidaDto {
        val resp = api.crearPartida(PartidaPostDto(jugador1Id, jugador2Id))
        if (!resp.isSuccessful) {
            throw IllegalStateException("HTTP ${resp.code()} ${resp.message()}")
        }
        val location = resp.headers()["Location"]
            ?: throw IllegalStateException("Falta header Location en respuesta 201")

        val id = location.substringAfterLast('/').toIntOrNull()
            ?: throw IllegalStateException("No se pudo parsear id desde Location=$location")


        return api.getPartida(id)
    }
}
