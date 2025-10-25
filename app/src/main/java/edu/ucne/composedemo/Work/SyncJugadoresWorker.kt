package edu.ucne.composedemo.Work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import edu.ucne.composedemo.Domain.useCase.PostPendientesUseCase
import edu.ucne.composedemo.Domain.useCase.SyncJugadoresUseCase
import edu.ucne.composedemo.Util.Resource

@HiltWorker
class SyncJugadoresWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val postPendientes: PostPendientesUseCase,
    private val syncFull: SyncJugadoresUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val a = postPendientes()
        if (a is Resource.Error) return Result.retry()
        val b = syncFull()
        return if (b is Resource.Success) Result.success() else Result.retry()
    }
}
