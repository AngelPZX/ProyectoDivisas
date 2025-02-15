package com.example.proyectodivisas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectodivisas.model.TipoCambio
import com.example.proyectodivisas.model.TipoCambioDao

@Database(entities = [TipoCambio::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tipoCambioDao(): TipoCambioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tipo_cambio_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}