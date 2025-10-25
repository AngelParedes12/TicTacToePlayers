package edu.ucne.composedemo.Di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import edu.ucne.composedemo.Data.Local.Database.JugadorDb
import edu.ucne.composedemo.Data.Local.Dao.JugadorDao
import edu.ucne.composedemo.Data.Repository.JugadorRepositorylmpl
import edu.ucne.composedemo.Domain.Repository.JugadorRepository
import edu.ucne.composedemo.Domain.useCase.EliminarJugadorUseCase
import edu.ucne.composedemo.Domain.useCase.GuardarJugadorUseCase
import edu.ucne.composedemo.Domain.useCase.JugadorUseCases
import edu.ucne.composedemo.Domain.useCase.ObtenerJugadorUseCase
import edu.ucne.composedemo.Domain.useCase.ObtenerJugadoresUseCase
import edu.ucne.composedemo.Domain.useCase.ValidarJugadorUseCase

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
    fun provideJugadorDao(db: JugadorDb): JugadorDao =
        db.JugadorDao()

    @Provides
    @Singleton
    fun provideJugadorRepository(dao: JugadorDao): JugadorRepository =
        JugadorRepositorylmpl(dao)

    @Provides
    fun provideJugadorUseCases(repository: JugadorRepository): JugadorUseCases {
        val validar = ValidarJugadorUseCase(repository)
        val obtenerTodos = ObtenerJugadoresUseCase(repository)
        return JugadorUseCases(
            validarJugador = validar,
            guardarJugador = GuardarJugadorUseCase(repository, validar),
            eliminarJugador = EliminarJugadorUseCase(repository),
            obtenerJugador = ObtenerJugadorUseCase(repository),
            obtenerJugadores = obtenerTodos
        )
    }
}
