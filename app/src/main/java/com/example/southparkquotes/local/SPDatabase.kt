package com.example.southparkquotes.local

// Hier solltest du den Import für die Entity hinzufügen
import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.southparkquotes.model.BackgroundImages
import com.example.southparkquotes.model.Character

@Database(entities = [Character::class, BackgroundImages::class], version = 2, exportSchema = false)
abstract class SPDatabase : RoomDatabase() {
    abstract val spDatabaseDao : SPDatabaseDao

    companion object {
        @Volatile // Für den Hauptspeicher, aktualisierung der Instanz
        private var INSTANCE: SPDatabase? = null

        fun createInstance(application: Application):SPDatabase {
            synchronized(this)
            {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        application.applicationContext,
                        SPDatabase::class.java,
                        "SPDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .addMigrations(MIGRATION_1_2)
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
        // Hier wird eine Migration hinzugefügt, um das Schema von Version 1 auf Version 2 zu aktualisieren
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Falls neue Eigenschaften in der Klasse erstellt werden, sollten sie dann durch
                // hier importierte SQL-Befehle zur Aktualisierung der Datenbank ausgeführt werden.
                // Wenn aber keine manuellen Änderungen erforderlich sind, kann man diese Methode leer lassen.
            }
        }
    }
}



