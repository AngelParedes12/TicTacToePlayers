package edu.ucne.composedemo.Data.Remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JugadorPostDto(
    val nombres: String,
    val email: String? = null
)
