package edu.ucne.composedemo.Domain.useCase.UseCaseJugador

import edu.ucne.composedemo.Domain.Repository.JugadorRepository

class ObtenerJugadoresUseCase(private val repo: JugadorRepository) {
    operator fun invoke() = repo.observe()
}
