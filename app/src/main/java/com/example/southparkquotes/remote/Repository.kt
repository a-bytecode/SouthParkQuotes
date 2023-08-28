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

    var charPick = 0

    var stan = Character(0, "Stan", R.drawable.stan_marsh_0,"Lets Rock!")
    var cartman = Character(1,"Cartman",R.drawable.eric_cartman,"Lets Rock!")
    var kyle = Character(2,"Kyle",R.drawable.kyle_broflovski,"Lets Rock!")
    var butters = Character(3, "Butters", R.drawable.buttersstotch,"Lets Rock!")

    var charList = mutableListOf(stan,kyle,butters,cartman)

    private val _selectedCharacterNameEntity = MutableLiveData<List<Character>>()
    val selectedCharacterNameEntity : LiveData<List<Character>>
        get() = _selectedCharacterNameEntity

    suspend fun getQuotesResponse(name:String) {
        try {
            dB.deleteAll()
            dB.resetIdSequence()
            val responseImageNameList = api.retrofitService.getCharacterAndQuotes(name)
            val newQuotes = responseImageNameList.filterNot { newQuote ->
                getAllDatabase.value?.any { existingQuote ->
                    existingQuote.id == newQuote.id || existingQuote.quote == newQuote.quote
                } ?:false
            }
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