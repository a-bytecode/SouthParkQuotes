package com.example.southparkquotes.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.southparkquotes.R
import com.example.southparkquotes.local.SPDatabase
import com.example.southparkquotes.model.ApiStatus
import com.example.southparkquotes.model.BackgroundImages
import com.example.southparkquotes.model.Character
import com.example.southparkquotes.model.MainViewModel


class Repository(private val api: SouthParkApiServiceQNumber.UserApi, private val database : SPDatabase) {

    val dB = database.spDatabaseDao
    val getAllDatabase = dB.getAll()

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
    //All Butters Voices:
    var buttersVoiceList : List<Int> = listOf(
        R.raw.butters1,
        R.raw.butters2,
        R.raw.butters3,
        R.raw.butters4,
        R.raw.butters5,
        R.raw.butters6,
        R.raw.butters7,
        R.raw.butters8,
        R.raw.butters9,
        R.raw.butters10,
        R.raw.butters11,
        R.raw.butters12,
        R.raw.butters13,
        R.raw.butters14,
        R.raw.butters15,
        R.raw.butters16,
        R.raw.butters17,
        R.raw.butters18,
        R.raw.butters19,
        R.raw.butters20,
        R.raw.butters21,
        R.raw.butters22
        )
    // All Kyle Voices:
    var kyleVoiceList : List<Int> = listOf(
        R.raw.kyle1,
        R.raw.kyle2,
        R.raw.kyle3,
        R.raw.kyle4,
        R.raw.kyle5,
        R.raw.kyle6,
        R.raw.kyle7,
        R.raw.kyle8,
        R.raw.kyle9,
        R.raw.kyle10,
        R.raw.kyle11,
        R.raw.kyle12,
        R.raw.kyle13,
        R.raw.kyle14,
        R.raw.kyle15,
        R.raw.kyle16,
        R.raw.kyle17,
        R.raw.kyle18,
        R.raw.kyle19,
        R.raw.kyle20,
        R.raw.kyle21
    )
    //All Stan Voices:
    var stanVoiceList : List<Int> = listOf(
        R.raw.stan1,
        R.raw.stan2,
        R.raw.stan3,
        R.raw.stan4,
        R.raw.stan5,
        R.raw.stan6,
        R.raw.stan7,
        R.raw.stan8,
        R.raw.stan9,
        R.raw.stan10,
        R.raw.stan11,
        R.raw.stan12,
        R.raw.stan13,
        R.raw.stan14,
        R.raw.stan15,
        R.raw.stan16

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
        R.drawable.background_20
    )
    // All Characters:
    var stan = Character(0, "Stan", R.drawable.stan_marsh_0,"Lets Rock!")
    var cartman = Character(1,"Cartman",R.drawable.eric_cartman,"Lets Rock!")
    var kyle = Character(2,"Kyle",R.drawable.kyle_broflovski,"Lets Rock!")
    var butters = Character(3, "Butters", R.drawable.buttersstotch,"Lets Rock!")

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

    fun getLastBackgroundImage(): LiveData<BackgroundImages?> {
        return dB.getLastBackgroundImage()
    }

    suspend fun getQuotesResponse(name:String,viewModel: MainViewModel) {
        try {
            viewModel.setAPIStatus(ApiStatus.LOADING)
            dB.deleteAll()
            dB.resetIdSequence()
            val responseImageNameList = api.retrofitService.getCharacterAndQuotes(name)
            val newQuotes = responseImageNameList

            if(newQuotes.isNotEmpty()) {
                _selectedCharacterNameEntity.value = responseImageNameList
                dB.insertCharacters(newQuotes)
            }

            viewModel.setAPIStatus(ApiStatus.START)

        } catch (e:Exception) {
            e.printStackTrace()
            viewModel.errorAPIStatus()
        }
    }
}