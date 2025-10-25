package edu.ucne.composedemo.Data.Mappers

import edu.ucne.composedemo.Data.Local.Entities.JugadorEntity
import edu.ucne.composedemo.Data.Remote.dto.JugadorDto
import edu.ucne.composedemo.Domain.Model.Jugador
import java.util.UUID

fun JugadorEntity.toDomain() = Jugador(
    id = id,
    remoteId = remoteId,
    nombres = nombres,
    apellidos = apellidos,
    telefono = telefono,
    email = email,
    partidas = partidas,
    isPendingCreate = isPendingCreate
)

fun Jugador.toEntity() = JugadorEntity(
    id = id,
    remoteId = remoteId,
    nombres = nombres,
    apellidos = apellidos,
    telefono = telefono,
    email = email,
    partidas = partidas,
    isPendingCreate = isPendingCreate
)

fun JugadorDto.toEntity() = JugadorEntity(
    id = UUID.randomUUID().toString(),
    remoteId = jugadorId,
    nombres = nombres,
    apellidos = null,
    telefono = null,
    email = email,
    partidas = 0,
    isPendingCreate = false
)
