package com.example.proyectodivisas.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.proyectodivisas.database.AppDatabase
import com.example.proyectodivisas.model.TipoCambio

class DivisasContentProvider : ContentProvider() {

    private lateinit var database: AppDatabase

    override fun onCreate(): Boolean {
        database = AppDatabase.getDatabase(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val moneda = selectionArgs?.get(0) // Código de la moneda (ej: USD)
        val fechaInicio = selectionArgs?.get(1)?.toLong() // Fecha de inicio en timestamp
        val fechaFin = selectionArgs?.get(2)?.toLong() // Fecha de fin en timestamp

        return database.tipoCambioDao().getTipoCambioPorMonedaYRango(
            moneda!!,
            fechaInicio!!,
            fechaFin!!
        )
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.com.example.proyectodivisas.tipo_cambio"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Insert not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Delete not supported")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Update not supported")
    }
}