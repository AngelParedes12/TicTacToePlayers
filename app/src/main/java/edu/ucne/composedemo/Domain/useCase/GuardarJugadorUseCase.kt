package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.JugadorRepository

class GuardarJugadorUseCase(
    private val repository: JugadorRepository,
    private val validarJugador: ValidarJugadorUseCase
) {
    suspend operator fun invoke(jugador: Jugador): Result<Boolean> {

        val validacion = validarJugador(jugador)
        if (validacion.isFailure) return Result.failure(validacion.exceptionOrNull()!!)

        val result = repository.save(jugador)
        return Result.success(result)
    }
}