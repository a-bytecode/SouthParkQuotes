package com.example.southparkquotes.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.southparkquotes.R
import com.example.southparkquotes.local.SPDatabase
import com.example.southparkquotes.model.Character


class Repository(private val api: SouthParkApiServiceQNumber.UserApi, private val database : SPDatabase) {

    val dB = database.spDatabaseDao
    val getAllDatabase = dB.getAll()

    // All Characters:
    var charPick = 0

    var stan = Character(0, "Stan", R.drawable.stan_marsh_0,"Lets Rock!")
    var cartman = Character(1,"Cartman",R.drawable.eric_cartman,"Lets Rock!")
    var kyle = Character(2,"Kyle",R.drawable.kyle_broflovski,"Lets Rock!")
    var butters = Character(3, "Butters", R.drawable.buttersstotch,"Lets Rock!")

    var charList = mutableListOf(stan,kyle,butters,cartman)

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

    private val _selectedCharacterNameEntity = MutableLiveData<List<Character>>()
    val selectedCharacterNameEntity : LiveData<List<Character>>
        get() = _selectedCharacterNameEntity

    suspend fun getQuotesResponse(name:String) {
        try {
            dB.deleteAll()
            dB.resetIdSequence()
            val responseImageNameList = api.retrofitService.getCharacterAndQuotes(name)
            Log.d("Repository004", "Name -> ${responseImageNameList[0].name}")
            val newQuotes = responseImageNameList
            if(newQuotes.isNotEmpty()) {
                _selectedCharacterNameEntity.value = responseImageNameList
                dB.insertCharacters(newQuotes)
                Log.d("Repository004", "Size of List -> ${selectedCharacterNameEntity.value?.size ?: 0}")
                Log.d("Repository005", "Full List -> ${selectedCharacterNameEntity.value}")
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}