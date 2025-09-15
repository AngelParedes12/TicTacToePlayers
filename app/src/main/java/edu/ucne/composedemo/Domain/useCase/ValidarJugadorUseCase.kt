package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.JugadorRepository

class ValidarJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Unit> {

        if (jugador.nombre.isBlank() || jugador.partidas < 0) {
            return Result.failure(Exception("Nombre vacío o partidas negativas"))
        }


        val jugadores = repository.getAll()
        val nombreRepetido = jugadores.any {
            it.nombre.equals(jugador.nombre, ignoreCase = true) &&
                    it.id != jugador.id
        }
        if (nombreRepetido) {
            return Result.failure(Exception("Ya existe un jugador con ese nombre"))
        }

        return Result.success(Unit)
    }
}