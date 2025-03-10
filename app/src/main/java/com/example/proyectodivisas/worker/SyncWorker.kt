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
            Log.d("SyncWorker", "Respuesta de la API recibida: ${response.conversion_rates}")

            val database = AppDatabase.getDatabase(applicationContext)
            val tipoCambioDao = database.tipoCambioDao()

            response.conversion_rates.forEach { (codigoDeMoneda, valor) ->
                val tipoCambio = TipoCambio(
                    idTipoCambio = 0, // Room generará automáticamente el ID
                    codigoDeMoneda = codigoDeMoneda,
                    valor = valor,
                    fecha = System.currentTimeMillis() // Usa la fecha actual
                )
                tipoCambioDao.insert(tipoCambio)
                Log.d("SyncWorker", "Insertado: $codigoDeMoneda -> $valor")
            }

            Log.d("SyncWorker", "Sincronización completada con éxito")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error en la sincronización", e)
            Result.failure()
        }
    }
}