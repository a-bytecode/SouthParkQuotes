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

    var stan = Character("Stan", R.drawable.stan_marsh_0, "Lets Rock it.")
    var cartman = Character("Cartman", R.drawable.eric_cartman, "Lets Rock it.")
    var kyle = Character("Kyle", R.drawable.kyle_broflovski, "Lets Rock it.")
    var butters = Character("Butters", R.drawable.buttersstotch, "Lets Rock it.")

    var charList = mutableListOf(stan,kyle,butters,cartman)

    private val _selectedCharacterNameEntity = MutableLiveData<List<Character>>()
    val selectedCharacterNameEntity : LiveData<List<Character>>
        get() = _selectedCharacterNameEntity

    suspend fun getQuotesResponse(name:String) {
        try {
            val responseImageNameList = api.retrofitService.getCharacterAndQuotes(name)
                _selectedCharacterNameEntity.value = responseImageNameList
                dB.insertCharacters(responseImageNameList)
                Log.d("Repository004", "Size of List -> ${selectedCharacterNameEntity.value?.size?: 0}")
                Log.d("Repository005", "Full List -> ${selectedCharacterNameEntity.value}")
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}