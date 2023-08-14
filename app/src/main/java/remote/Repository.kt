package remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.southparkquotes.R
import model.Character

class Repository(private val api: SouthParkApiServiceQNumber.UserApi, private val charApi : SPQuotesApiByCharacters.UserApi) {

    var charPick = 0

    var stan = Character("Stan", R.drawable.stan_marsh_0, "Lets Rock it.")
    var cartman = Character("Cartman", R.drawable.eric_cartman, "Lets Rock it.")
    var kyle = Character("Kyle", R.drawable.kyle_broflovski, "Lets Rock it.")
    var butters = Character("Butters", R.drawable.buttersstotch, "Lets Rock it.")

    var charList = mutableListOf(stan,kyle,butters,cartman)

    private var _characterListResponse = MutableLiveData<List<Character>>()
    val characterListResponse : LiveData<List<Character>>
        get() = _characterListResponse

    private var _characterImageResponse = MutableLiveData<List<Character>>()
    val characterImageResponse : LiveData<List<Character>>
        get() = _characterImageResponse

    private val _selectedCharacterName = MutableLiveData<String>()
    val selectedCharacterName : LiveData<String>
        get() = _selectedCharacterName

    suspend fun getQuotesNumber(number : String) {
        try {
            val responseCharList = api.retrofitService.getQuotesNumbers(number)
            _characterListResponse.value = responseCharList
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getCharacterImage(characterName: String) {
        try {
            val responseCharImage = charApi.retrofitService.getCharacters(characterName)
            _characterImageResponse.value = responseCharImage
            _selectedCharacterName.value = characterName
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}