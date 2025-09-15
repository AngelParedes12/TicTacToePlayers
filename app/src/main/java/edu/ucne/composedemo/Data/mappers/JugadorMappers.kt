package edu.ucne.composedemo.Data.mappers

import edu.ucne.composedemo.Data.Local.Entities.JugadorEntity
import edu.ucne.composedemo.Domain.Model.Jugador

fun JugadorEntity.toDomain() = Jugador(
    id = jugadorId ?: 0,
    nombre = nombres,
    partidas = partidas
)

fun Jugador.toEntity() = JugadorEntity(
    jugadorId = id,
    nombres = nombre,
    partidas = partidas
)