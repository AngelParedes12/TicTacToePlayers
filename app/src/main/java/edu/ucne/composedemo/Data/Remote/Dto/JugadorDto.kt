package edu.ucne.composedemo.Data.Remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JugadorDto(
    val jugadorId: Long? = null,
    val nombres: String,
    val email: String? = null
)
