package edu.ucne.composedemo.Domain.useCase

data class JugadorUseCases(
    val guardarLocal: GuardarJugadorLocalUseCase,
    val obtenerTodos: ObtenerJugadoresUseCase,
    val obtener: ObtenerJugadorUseCase,
    val eliminar: EliminarJugadorUseCase,
    val validar: ValidarJugadorUseCase,
    val postPendientes: PostPendientesUseCase,
    val syncFull: SyncJugadoresUseCase
)
