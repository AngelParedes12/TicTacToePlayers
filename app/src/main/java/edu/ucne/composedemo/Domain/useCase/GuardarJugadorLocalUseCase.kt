package edu.ucne.composedemo.Domain.useCase

import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import edu.ucne.composedemo.Util.Resource
import javax.inject.Inject

class GuardarJugadorLocalUseCase @Inject constructor(
    private val repo: JugadorRepository
) {
    suspend operator fun invoke(
        nombres: String,
        email: String?
    ): Resource<Jugador> {
        return repo.guardarLocal(
            nombres = nombres,
            apellidos = null,
            telefono = null,
            email = email,
            partidas = 0
        )
    }
}
