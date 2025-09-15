package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import kotlinx.coroutines.flow.Flow

class ObtenerJugadoresUseCase(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> {
        return repository.getAllFlow()
    }
}