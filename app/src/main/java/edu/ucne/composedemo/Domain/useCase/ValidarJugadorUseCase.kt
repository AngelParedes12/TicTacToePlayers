package edu.ucne.composedemo.Domain.useCase

class ValidarJugadorUseCase {
    operator fun invoke(n: String, p: Int) = n.isNotBlank() && p >= 0
}
