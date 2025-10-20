package edu.ucne.composedemo.Di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import edu.ucne.composedemo.Data.Local.Jugador.Entities.Database.JugadorDb
import edu.ucne.composedemo.Data.Local.Jugador.Entities.Dao.JugadorDao
import edu.ucne.composedemo.Data.Local.Partidas.Dao.PartidaDao
import edu.ucne.composedemo.Data.Repository.JugadorRepositorylmpl
import edu.ucne.composedemo.Data.Repository.PartidaRepositoryImpl
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import edu.ucne.composedemo.Domain.Repository.PartidaRepository
import edu.ucne.composedemo.Domain.useCase.UseCaseJugador.EliminarJugadorUseCase
import edu.ucne.composedemo.Domain.useCase.UseCaseJugador.GuardarJugadorUseCase
import edu.ucne.composedemo.Domain.useCase.UseCaseJugador.JugadorUseCases
import edu.ucne.composedemo.Domain.useCase.UseCaseJugador.ObtenerJugadorUseCase
import edu.ucne.composedemo.Domain.useCase.UseCaseJugador.ObtenerJugadoresUseCase
import edu.ucne.composedemo.Domain.useCase.UseCaseJugador.ValidarJugadorUseCase
import edu.ucne.composedemo.Domain.useCase.PartidaUseCases
import edu.ucne.composedemo.Domain.useCase.ListarPartidasUseCase
import edu.ucne.composedemo.Domain.useCase.ObtenerPartidaUseCase
import edu.ucne.composedemo.Domain.useCase.GuardarPartidaUseCase
import edu.ucne.composedemo.Domain.useCase.EliminarPartidaUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context): JugadorDb =
        Room.databaseBuilder(appContext, JugadorDb::class.java, "JugadorDb")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideJugadorDao(db: JugadorDb): JugadorDao = db.JugadorDao()

    @Provides
    fun providePartidaDao(db: JugadorDb): PartidaDao = db.PartidaDao()

    @Provides
    @Singleton
    fun provideJugadorRepository(dao: JugadorDao): JugadorRepository =
        JugadorRepositorylmpl(dao)

    @Provides
    @Singleton
    fun providePartidaRepository(
        partidaDao: PartidaDao,
        jugadorDao: JugadorDao
    ): PartidaRepository = PartidaRepositoryImpl(partidaDao, jugadorDao)

    @Provides
    fun provideJugadorUseCases(repository: JugadorRepository): JugadorUseCases {
        val validar = ValidarJugadorUseCase(repository)
        return JugadorUseCases(
            validarJugador = validar,
            guardarJugador = GuardarJugadorUseCase(repository, validar),
            eliminarJugador = EliminarJugadorUseCase(repository),
            obtenerJugador = ObtenerJugadorUseCase(repository),
            obtenerJugadores = ObtenerJugadoresUseCase(repository)
        )
    }

    @Provides
    fun providePartidaUseCases(repo: PartidaRepository) = PartidaUseCases(
        listarPartidas = ListarPartidasUseCase(repo),
        obtenerPartida = ObtenerPartidaUseCase(repo),
        guardarPartida = GuardarPartidaUseCase(repo),
        eliminarPartida = EliminarPartidaUseCase(repo)
    )
}
