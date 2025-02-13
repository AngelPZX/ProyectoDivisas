package com.example.proyectodivisas.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.proyectodivisas.database.AppDatabase
import com.example.proyectodivisas.model.TipoCambio
import com.example.proyectodivisas.network.ExchangeRateApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val response = ExchangeRateApiService.api.getLatestRates()
            val database = AppDatabase.getDatabase(applicationContext)
            val tipoCambioDao = database.tipoCambioDao()

            response.conversion_rates.forEach { (codigoDeMoneda, valor) ->
                val tipoCambio = TipoCambio(
                    idTipoCambio = 0, // Auto-generado por Room
                    codigoDeMoneda = codigoDeMoneda,
                    valor = valor,
                    time_last_update = response.time_last_update_unix,
                    time_next_update = response.time_next_update_unix
                )
                tipoCambioDao.insert(tipoCambio)
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}