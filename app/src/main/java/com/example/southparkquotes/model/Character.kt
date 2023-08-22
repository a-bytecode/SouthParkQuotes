package com.example.southparkquotes.model

import androidx.room.Entity
import com.squareup.moshi.Json


@Entity(primaryKeys = ["name"])
data class Character(

    @Json(name = "character")
    var name : String,

    var imageResource: Int? = null, // Hier speichern wir die ID des Bildes (Resource-Id)

    @Json(name = "quote")
    var quote : String

)