package edu.ucne.composedemo.Di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import edu.ucne.composedemo.Data.Remote.TicTacToeApi
import edu.ucne.composedemo.Domain.Repository.MovimientosRepository
import edu.ucne.composedemo.Data.Repository.MovimientosRepositoryImpl
import edu.ucne.composedemo.Domain.Repository.PartidasRepository
import edu.ucne.composedemo.Data.Repository.PartidasRepositoryImpl
import edu.ucne.composedemo.Domain.useCase.CreatePartidaUseCase
import edu.ucne.composedemo.Domain.useCase.EnsurePartidaUseCase
import edu.ucne.composedemo.Domain.useCase.GetPartidaUseCase
import edu.ucne.composedemo.Domain.useCase.ListPartidasUseCase
import edu.ucne.composedemo.Domain.useCase.PartidasUseCases

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
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
        return Retrofit.Builder()
            .baseUrl("https://gestionhuacalesapi.azurewebsites.net/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

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

    @Provides
    @Singleton
    fun providePartidasUseCases(repo: PartidasRepository): PartidasUseCases =
        PartidasUseCases(
            create = CreatePartidaUseCase(repo),
            ensure = EnsurePartidaUseCase(repo),
            get = GetPartidaUseCase(repo),
            list = ListPartidasUseCase(repo)
        )
}
