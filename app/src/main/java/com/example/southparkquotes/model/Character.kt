package com.example.southparkquotes.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0, // Eindeutige ID für die Entität, automatisch generiert

    @Json(name = "character")
    var name : String,

    var imageResource: Int? = null, // Hier speichern wir die ID des Bildes (Resource-Id)

    @Json(name = "quote")
    var quote : String,

    @Ignore
    var voiceList : List<Int>
) {
    // Sekundärer Konstruktor für Room
    constructor(name: String, quote: String) : this(0, name, null, quote, emptyList())
}