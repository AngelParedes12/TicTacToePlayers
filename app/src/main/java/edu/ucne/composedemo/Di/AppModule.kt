package edu.ucne.composedemo.Di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.composedemo.Data.Local.AppDb
import edu.ucne.composedemo.Data.Local.Dao.JugadorDao
import edu.ucne.composedemo.Data.Remote.JugadorRemoteDataSource
import edu.ucne.composedemo.Data.Remote.JugadoresApiService
import edu.ucne.composedemo.Data.Remote.TicTacToeApi
import edu.ucne.composedemo.Data.Repository.JugadorRepositoryImpl
import edu.ucne.composedemo.Data.Repository.MovimientosRepositoryImpl
import edu.ucne.composedemo.Data.Repository.PartidasRepositoryImpl
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import edu.ucne.composedemo.Domain.Repository.MovimientosRepository
import edu.ucne.composedemo.Domain.Repository.PartidasRepository
import edu.ucne.composedemo.Domain.useCase.*
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val log = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(log).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://gestionhuacalesapi.azurewebsites.net/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): TicTacToeApi =
        retrofit.create(TicTacToeApi::class.java)

    @Provides
    @Singleton
    fun provideMovimientosRepo(api: TicTacToeApi): MovimientosRepository =
        MovimientosRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePartidasRepository(api: TicTacToeApi): PartidasRepository =
        PartidasRepositoryImpl(api)

    // Offline-First Jugadores
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext c: Context): AppDb =
        Room.databaseBuilder(c, AppDb::class.java, "app.db").build()

    @Provides
    fun provideJugadorDao(db: AppDb): JugadorDao = db.jugadorDao()

    @Provides
    @Singleton
    fun provideApiJugadores(retrofit: Retrofit): JugadoresApiService =
        retrofit.create(JugadoresApiService::class.java)

    @Provides
    @Singleton
    fun provideJugadorRemote(api: JugadoresApiService) = JugadorRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideJugadorRepository(
        dao: JugadorDao,
        remote: JugadorRemoteDataSource
    ): JugadorRepository = JugadorRepositoryImpl(dao, remote)

    @Provides
    @Singleton
    fun provideJugadorUseCases(repo: JugadorRepository) = JugadorUseCases(
        guardarLocal = GuardarJugadorLocalUseCase(repo),
        obtenerTodos = ObtenerJugadoresUseCase(repo),
        obtener = ObtenerJugadorUseCase(repo),
        eliminar = EliminarJugadorUseCase(repo),
        validar = ValidarJugadorUseCase(),
        postPendientes = PostPendientesUseCase(repo),
        syncFull = SyncJugadoresUseCase(repo)
    )
}
