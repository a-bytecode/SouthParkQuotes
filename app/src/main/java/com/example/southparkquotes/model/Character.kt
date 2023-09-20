package com.example.southparkquotes.model

import androidx.room.*
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

) : CharacterVoiceList {
    @Ignore
    override lateinit var voiceList: List<Int>
}

interface CharacterVoiceList {
    val voiceList: List<Int>
}