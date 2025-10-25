package edu.ucne.composedemo.Domain.useCase.UseCaseJugador

class ValidarJugadorUseCase {
    operator fun invoke(n: String, p: Int) = n.isNotBlank() && p >= 0
}
