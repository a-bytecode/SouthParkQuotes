package remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.southparkquotes.R
import model.Character


class Repository(private val api: SouthParkApiServiceQNumber.UserApi) {

    var charPick = 0

    var stan = Character("Stan", R.drawable.stan_marsh_0, "Lets Rock it.")
    var cartman = Character("Cartman", R.drawable.eric_cartman, "Lets Rock it.")
    var kyle = Character("Kyle", R.drawable.kyle_broflovski, "Lets Rock it.")
    var butters = Character("Butters", R.drawable.buttersstotch, "Lets Rock it.")

    var charList = mutableListOf(stan,kyle,butters,cartman)

    private var _characterListResponse = MutableLiveData<List<Character>>()
    val characterListResponse : LiveData<List<Character>>
        get() = _characterListResponse

    private val _selectedCharacterName = MutableLiveData<String>()
    val selectedCharacterName : LiveData<String>
        get() = _selectedCharacterName

    suspend fun getQuotesNumber(number : String, name:String) {
        try {
            val responseCharList = api.retrofitService.getQuotesNumbers(number)
            val responseImageNameList = api.retrofitService.getCharacterAndQuotes(name)
            _characterListResponse.value = responseCharList
            if (responseImageNameList.isNotEmpty()) {
                val characterName = responseImageNameList[0].name
                _selectedCharacterName.postValue(characterName)
                Log.d("Repository004", _selectedCharacterName.value ?: "Nix")
            }

        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun setSelectedCharacterName(newName: String) {
        _selectedCharacterName.postValue(newName)
        Log.d("Repository003", newName)

    }
}