package com.example.proyectodivisas.worker

import android.content.Context
import android.util.Log
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
            Log.d("SyncWorker", "Iniciando sincronización...")
            val response = ExchangeRateApiService.api.getLatestRates()
            Log.d("SyncWorker", "Respuesta de la API recibida: ${response.conversion_rates}") // Ver datos de la API
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
                Log.d("SyncWorker", "Insertado: $codigoDeMoneda -> $valor") // Ver inserción en la base de datos

            }
            Log.d("SyncWorker", "Sincronización completada con éxito") // Mensaje de éxito
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error en la sincronización", e) // Mensaje de error
            Result.failure()
        }
    }
}