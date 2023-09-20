package com.example.southparkquotes.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.southparkquotes.R
import com.example.southparkquotes.local.SPDatabase
import com.example.southparkquotes.model.BackgroundImages
import com.example.southparkquotes.model.Character


class Repository(private val api: SouthParkApiServiceQNumber.UserApi, private val database : SPDatabase) {

    val dB = database.spDatabaseDao
    val getAllDatabase = dB.getAll()

    // All Characters:
    var charPick = 0

    // All Cartman Voices:
    var cartmanVoiceList : List<Int> = listOf(
        R.raw.cartman1,
        R.raw.cartman2,
        R.raw.cartman3,
        R.raw.cartman5,
        R.raw.cartman6,
        R.raw.cartman7,
        R.raw.cartman8,
        R.raw.cartman9,
        R.raw.cartman10,
        R.raw.cartman11,
        R.raw.cartman12,
        R.raw.cartman13,
        R.raw.cartman14,
        R.raw.cartman15,
        R.raw.cartman16,
        R.raw.cartman17,
        R.raw.cartman18,
        R.raw.cartman19,
        R.raw.cartman20,
        R.raw.cartman21,
        R.raw.cartman22,
        R.raw.cartman23,
        R.raw.cartman24,
        R.raw.cartman25,
        R.raw.cartman26,
        R.raw.cartman27,
        R.raw.cartman28,
        R.raw.cartman29,
        R.raw.cartman30,
        R.raw.cartman31,
        R.raw.cartman32,
        R.raw.cartman33,
        R.raw.cartman34,
        R.raw.cartman35,
        R.raw.cartman36,
        R.raw.cartman37,
        R.raw.cartman38,
        R.raw.cartman39,
        R.raw.cartman40
    )

    // All Background Pictures:
    val backgroundPictureList : MutableList<Int> = mutableListOf(
        R.drawable.background_1,
        R.drawable.background_2,
        R.drawable.background_3,
        R.drawable.background_4,
        R.drawable.background_5,
        R.drawable.background_6,
        R.drawable.background_7,
        R.drawable.background_8,
        R.drawable.background_9,
        R.drawable.background_10,
        R.drawable.background_11,
        R.drawable.background_12,
        R.drawable.background_13,
        R.drawable.background_14,
        R.drawable.background_15,
        R.drawable.background_16,
        R.drawable.background_17,
        R.drawable.background_18,
        R.drawable.background_19,
        R.drawable.background_20,
    )

    var stan = Character(0, "Stan", R.drawable.stan_marsh_0,"Lets Rock!", cartmanVoiceList)
    var cartman = Character(1,"Cartman",R.drawable.eric_cartman,"Lets Rock!",cartmanVoiceList)
    var kyle = Character(2,"Kyle",R.drawable.kyle_broflovski,"Lets Rock!",cartmanVoiceList)
    var butters = Character(3, "Butters", R.drawable.buttersstotch,"Lets Rock!",cartmanVoiceList)

    var charList = mutableListOf(stan,kyle,butters,cartman)


    private val _selectedCharacterNameEntity = MutableLiveData<List<Character>>()
    val selectedCharacterNameEntity : LiveData<List<Character>>
        get() = _selectedCharacterNameEntity

    private val _selectedBackground = MutableLiveData<BackgroundImages>()
    val selectedBackground : LiveData<BackgroundImages>
        get() = _selectedBackground

    suspend fun insertImages(backgroundImages: BackgroundImages) {
        dB.insertImages(backgroundImages)
        _selectedBackground.value = backgroundImages
    }


    suspend fun getQuotesResponse(name:String) {
        try {
            dB.deleteAll()
            dB.resetIdSequence()
            val responseImageNameList = api.retrofitService.getCharacterAndQuotes(name)
            val newQuotes = responseImageNameList

            if(newQuotes.isNotEmpty()) {
                _selectedCharacterNameEntity.value = responseImageNameList
                dB.insertCharacters(newQuotes)
            }

        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}