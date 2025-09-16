package edu.ucne.composedemo.Domain.useCase.UseCaseJugador

import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import edu.ucne.composedemo.Domain.Model.Jugador

class EliminarJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador) {
        repository.delete(jugador)
    }
}