package model

import android.os.Build
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.southparkquotes.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import remote.Repository
import remote.SPQuotesApiByCharacters
import remote.SouthParkApiServiceQNumber

class MainViewModel : ViewModel() {

    var api = SouthParkApiServiceQNumber.UserApi

    var charApi = SPQuotesApiByCharacters.UserApi

    var repo = Repository(api,charApi)

    val characterNameLiveData = MutableLiveData<String>() // Aktualisierung des Namens durch Mutable Live Data

    val charListRequest = repo.characterListResponse

    var selectedCharacterImage = repo.characterImageResponse

    val selectedCharacterName = repo.selectedCharacterName

    var selectedImageResource: Int = R.drawable.stan_marsh_0 // Hier Standardwert einsetzen

    fun switchCharactersLeft(imageView: ImageView) {

        repo.charPick = (repo.charPick +1) % repo.charList.size

        repo.charList[repo.charPick].imageResource?.let {
            imageView.setImageResource(it)
            selectedImageResource = it
            characterNameLiveData.value = repo.charList[repo.charPick].name
            getCharacterImage(repo.charList[repo.charPick].name)
        }
     }

    fun switchCharactersRight(imageView: ImageView) {

        repo.charPick = (repo.charPick - 1 + repo.charList.size) % repo.charList.size

        repo.charList[repo.charPick].imageResource?.let {
            imageView.setImageResource(it)
            selectedImageResource = it
        }

        characterNameLiveData.value = repo.charList[repo.charPick].name
        getCharacterImage(characterNameLiveData.value!!)
    }

    fun updateCharacterName(newName: String) {

        for (character in repo.charList) {

            if (character.name == newName) {
                characterNameLiveData.value = newName
                break
            }
        }
    }

    fun addNumber(editNumber: EditText) {

        val currentNumber = editNumber.text.toString().toIntOrNull() ?: 1 // Elvis-Operator,
        // wenn der Anfangswert keine gültige Zahl hat oder null ist wird er Automatisch auf
        // eine 1 gesetzt.
        val newNumber = currentNumber +1

        if (newNumber >= 5) {
            editNumber.setText((5).toString())
        } else {
            editNumber.setText(newNumber.toString())
        }
    }

    fun subNumber(editNumber: EditText) {

        val currentNumber = editNumber.text.toString().toIntOrNull() ?: 1 // Elvis-Operator,
        // wenn der Anfangswert keine gültige Zahl hat oder null ist wird er Automatisch auf
        // eine 1 gesetzt.
        val newNumber = currentNumber -1

        if (newNumber <= 0) {
            editNumber.setText((0).toString())
        } else {
            editNumber.setText(newNumber.toString())
        }
    }

    fun anmitateTextView(inputText: TextView) {

        val text = inputText.text.toString()
        val stringBuilder = java.lang.StringBuilder()

        GlobalScope.launch(Dispatchers.Default) {

            for (char in text) {
                stringBuilder.append(char)
                withContext(Dispatchers.Main) {
                    inputText.text = stringBuilder.toString()
                }
                delay(300)
            }
        }
    }

    fun setVisible(input: ImageView) {
        input.visibility = View.VISIBLE
    }

    fun setGone(input: ImageView) {
        input.visibility = View.GONE
    }

    fun getQuotesNumber(number:String) {
        viewModelScope.launch {
            try {
                repo.getQuotesNumber(number)
            } catch(e:Exception) {
                Log.d("Error im ViewModel","${charListRequest.value}")
            }
        }
    }

    fun getCharacterImage(characterName: String) {
        viewModelScope.launch {
            try {
                repo.getCharacterImage(characterName)
                Log.d("CharName-Test","${characterName}")

            } catch (e:Exception) {
                Log.d("Error im ViewModel","${selectedCharacterImage.value}")
            }
        }
    }
}