package edu.ucne.composedemo.Data.Remote

import edu.ucne.composedemo.Data.Remote.dto.JugadorDto
import edu.ucne.composedemo.Data.Remote.dto.JugadorPostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JugadoresApiService {
    @GET("api/Jugadores")
    suspend fun getAll(): Response<List<JugadorDto>>

    @POST("api/Jugadores")
    suspend fun create(@Body body: JugadorPostDto): Response<JugadorDto>
}
