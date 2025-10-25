package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import javax.inject.Inject

class GuardarJugadorUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador) {
        repository.guardarLocal(
            nombres = jugador.nombres,
            apellidos = jugador.apellidos,
            telefono = jugador.telefono,
            email = jugador.email,
            partidas = jugador.partidas
        )
    }
}
