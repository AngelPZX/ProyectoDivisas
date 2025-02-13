package com.example.proyectodivisas.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TipoCambioDao {
    @Insert
    suspend fun insert(tipoCambio: TipoCambio)

    @Query("SELECT * FROM tipo_cambio")
    fun getAll(): List<TipoCambio>
}