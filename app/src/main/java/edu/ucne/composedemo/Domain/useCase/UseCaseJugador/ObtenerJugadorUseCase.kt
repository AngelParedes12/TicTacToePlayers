package edu.ucne.composedemo.Domain.useCase.UseCaseJugador

import edu.ucne.composedemo.Domain.Repository.JugadorRepository

class ObtenerJugadorUseCase(private val repo: JugadorRepository) {
    suspend operator fun invoke(id: String) = repo.obtener(id)
}
