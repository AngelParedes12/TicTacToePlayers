package edu.ucne.composedemo.Domain.useCase.UseCaseJugador

import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import edu.ucne.composedemo.Domain.Model.Jugador
class ObtenerJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int): Jugador? {
        return repository.find(id)
    }
}