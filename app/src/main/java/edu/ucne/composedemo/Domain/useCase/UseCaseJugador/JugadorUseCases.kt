package edu.ucne.composedemo.Domain.useCase.UseCaseJugador
data class JugadorUseCases(
    val validarJugador: ValidarJugadorUseCase,
    val guardarJugador: GuardarJugadorUseCase,
    val eliminarJugador: EliminarJugadorUseCase,
    val obtenerJugador: ObtenerJugadorUseCase,
    val obtenerJugadores: ObtenerJugadoresUseCase
)