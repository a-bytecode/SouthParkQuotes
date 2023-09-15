package com.example.southparkquotes.local

// Hier solltest du den Import für die Entity hinzufügen
import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.southparkquotes.model.BackgroundImages
import com.example.southparkquotes.model.Character

@Database(entities = [Character::class, BackgroundImages::class], version = 1, exportSchema = false)
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
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}



