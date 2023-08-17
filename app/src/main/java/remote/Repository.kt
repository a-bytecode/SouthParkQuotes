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

    private val _selectedCharacterName = MutableLiveData<List<Character>>()
    val selectedCharacterName : LiveData<List<Character>>
        get() = _selectedCharacterName

    suspend fun getQuotesResponse(name:String) {
        try {
            val responseImageNameList = api.retrofitService.getCharacterAndQuotes(name)
                _selectedCharacterName.value = responseImageNameList
                Log.d("Repository004", "Size of List -> ${selectedCharacterName.value?.size?: 0}")

        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}