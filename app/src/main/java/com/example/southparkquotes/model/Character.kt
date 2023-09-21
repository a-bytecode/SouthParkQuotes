package com.example.southparkquotes.model

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Entity(tableName = "characters")
@Parcelize // Percalize Plugin aktiviert um die übergabe meiner Custom Klasse als Argument zu gewährleisten.
data class Character(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0, // Eindeutige ID für die Entität, automatisch generiert

    @Json(name = "character")
    var name : String,

    var imageResource: Int? = null, // Hier speichern wir die ID des Bildes (Resource-Id)

    @Json(name = "quote")
    var quote : String,

) : CharacterVoiceList, Parcelable {
    @IgnoredOnParcel
    @Ignore
    override lateinit var voiceList: List<Int>
}
// Interface angehängt um die Voice List von der RoomDatabase zu trennen.
interface CharacterVoiceList {
    val voiceList: List<Int>
}