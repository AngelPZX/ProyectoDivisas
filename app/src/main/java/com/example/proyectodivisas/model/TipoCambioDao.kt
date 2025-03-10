package com.example.proyectodivisas.model

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TipoCambioDao {
    @Insert
    suspend fun insert(tipoCambio: TipoCambio)

    @Query("SELECT * FROM tipo_cambio WHERE codigoDeMoneda = :moneda AND fecha BETWEEN :fechaInicio AND :fechaFin")
    fun getTipoCambioPorMonedaYRango(moneda: String, fechaInicio: Long, fechaFin: Long): Cursor
}