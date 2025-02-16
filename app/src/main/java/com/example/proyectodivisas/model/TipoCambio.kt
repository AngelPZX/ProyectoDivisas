package com.example.proyectodivisas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipo_cambio")
data class TipoCambio(
    @PrimaryKey(autoGenerate = true) val idTipoCambio: Int,
    val codigoDeMoneda: String,
    val valor: Double,
    val time_last_update: Long,
    val time_next_update: Long
)