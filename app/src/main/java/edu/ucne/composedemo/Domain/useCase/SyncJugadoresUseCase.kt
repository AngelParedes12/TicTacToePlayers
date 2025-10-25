package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Repository.JugadorRepository

class SyncJugadoresUseCase(private val repo: JugadorRepository) {
    suspend operator fun invoke() = repo.syncFull()
}
