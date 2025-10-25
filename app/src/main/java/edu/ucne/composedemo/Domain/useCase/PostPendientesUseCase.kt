package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Repository.JugadorRepository

class PostPendientesUseCase(private val repo: JugadorRepository) {
    suspend operator fun invoke() = repo.postPendientes()
}
