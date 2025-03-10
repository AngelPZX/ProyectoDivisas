package com.example.proyectodivisas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyectodivisas.model.TipoCambio
import com.example.proyectodivisas.model.TipoCambioDao

@Database(entities = [TipoCambio::class], version = 3)
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
                    .addMigrations(MIGRATION_2_3) // Agrega la migración
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Aquí defines cómo migrar de la versión 2 a la 3
        // Por ejemplo, si agregaste un nuevo campo:
        database.execSQL("ALTER TABLE tipo_cambio ADD COLUMN nuevo_campo TEXT")
    }
}

