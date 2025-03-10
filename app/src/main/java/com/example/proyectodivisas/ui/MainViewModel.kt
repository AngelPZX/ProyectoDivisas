package com.example.proyectodivisas.ui

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectodivisas.model.TipoCambio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context) : ViewModel() {

    private val _datosGrafica = MutableStateFlow<List<TipoCambio>>(emptyList())
    val datosGrafica: StateFlow<List<TipoCambio>> get() = _datosGrafica

    fun cargarDatos(moneda: String, fechaInicio: Long, fechaFin: Long) {
        viewModelScope.launch {
            val cursor: Cursor? = context.contentResolver.query(
                Uri.parse("content://com.example.proyectodivisas.provider/tipo_cambio"),
                null,
                "codigoDeMoneda = ? AND fecha BETWEEN ? AND ?",
                arrayOf(moneda, fechaInicio.toString(), fechaFin.toString()),
                null
            )

            val datos = mutableListOf<TipoCambio>()
            cursor?.use {
                while (it.moveToNext()) {
                    val id = it.getLong(it.getColumnIndex("idTipoCambio"))
                    val codigo = it.getString(it.getColumnIndex("codigoDeMoneda"))
                    val valor = it.getDouble(it.getColumnIndex("valor"))
                    val fecha = it.getLong(it.getColumnIndex("fecha"))
                    datos.add(TipoCambio(id.toInt(), codigo, valor, fecha))
                }
            }

            _datosGrafica.value = datos
        }
    }
}