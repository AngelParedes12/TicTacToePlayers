package edu.ucne.composedemo.Domain.Repository

import edu.ucne.composedemo.Domain.Model.Jugador
import edu.ucne.composedemo.Util.Resource
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observe(): Flow<List<Jugador>>
    suspend fun guardarLocal(nombres: String, apellidos: String?, telefono: String?, email: String?, partidas: Int): Resource<Jugador>
    suspend fun postPendientes(): Resource<Unit>
    suspend fun syncFull(): Resource<Unit>
    suspend fun obtener(id: String): Jugador?
    suspend fun eliminar(id: String): Resource<Unit>
}
