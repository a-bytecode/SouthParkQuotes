package com.example.southparkquotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "backgroundImages")
class BackgroundImages (

    @PrimaryKey(autoGenerate = true)
    val imageID : Int = 0, // Primärschlüssel
    val resourceID : Int // ID des Bildes

    )
