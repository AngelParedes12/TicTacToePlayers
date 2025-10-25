package edu.ucne.composedemo.Data.Remote

import edu.ucne.composedemo.Data.Remote.dto.JugadorDto
import edu.ucne.composedemo.Data.Remote.dto.JugadorPostDto
import edu.ucne.composedemo.Util.Resource
import javax.inject.Inject

class JugadorRemoteDataSource @Inject constructor(
    private val api: JugadoresApiService
) {
    suspend fun getAll(): Resource<List<JugadorDto>> {
        return try {
            val r = api.getAll()
            if (r.isSuccessful) Resource.Success(r.body().orEmpty()) else Resource.Error("HTTP ${r.code()}")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }

    suspend fun create(dto: JugadorPostDto): Resource<JugadorDto> {
        return try {
            val r = api.create(dto)
            if (r.isSuccessful && r.body() != null) Resource.Success(r.body()!!) else Resource.Error("HTTP ${r.code()}")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }
}
