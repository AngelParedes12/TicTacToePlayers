package edu.ucne.composedemo.Domain.Repository

import edu.ucne.composedemo.Data.Remote.Dto.PartidaDto

interface PartidasRepository {
    suspend fun exists(id: Int): Boolean
    suspend fun create(jugador1Id: Int, jugador2Id: Int): PartidaDto
    suspend fun get(id: Int): PartidaDto
}
