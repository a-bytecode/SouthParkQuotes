package model

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.southparkquotes.R
import kotlinx.coroutines.*
import remote.Repository
import remote.SouthParkApiServiceQNumber

class MainViewModel : ViewModel() {

    var api = SouthParkApiServiceQNumber.UserApi

    var repo = Repository(api)

    val characterNameLiveData = repo.selectedCharacterName // Aktualisierung des Namens durch Mutable Live Data

    val charListRequest = repo.characterListResponse

    var popupMenuCallback: PopupMenuCallback? = null

    var selectedImageResource: Int = R.drawable.stan_marsh_0 // Hier Standardwert einsetzen

    fun switchCharactersLeft(imageView: ImageView) {

        repo.charPick = (repo.charPick +1) % repo.charList.size

        repo.charList[repo.charPick].imageResource?.let {
            imageView.setImageResource(it)
            selectedImageResource = it
            val characterName = repo.charList[repo.charPick].name
            repo.setSelectedCharacterName(characterName)
            getQuotesResponse("1",characterName)
        }
     }

    fun switchCharactersRight(imageView: ImageView) {

        repo.charPick = (repo.charPick - 1 + repo.charList.size) % repo.charList.size

        repo.charList[repo.charPick].imageResource?.let {
            imageView.setImageResource(it)
            selectedImageResource = it
            val characterName = repo.charList[repo.charPick].name
            repo.setSelectedCharacterName(characterName)
            getQuotesResponse("1",characterName)
          }
     }

    fun updateCharacterName(newName: String) {
            for (character in repo.charList) {

                if (character.name == newName) {
                    repo.setSelectedCharacterName(newName)
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

    fun getQuotesResponse(number:String, name: String) {
        viewModelScope.launch {
            try {
                repo.getQuotesResponse(number, name)
                Log.d("GETQUOTES001","${name}")

            } catch(e:Exception) {
                Log.d("GETQUOTES001","${charListRequest.value}")
            }
        }
    }

    fun handlePopupMenuAction(actionId: Int,context: Context, callback: PopupMenuCallback) {
        when (actionId) {
            R.id.pop_up_menu_home -> {
               popupMenuCallback?.navigateToHome()
            }
            R.id.pop_up_menu_settings -> {
                // TODO: Implementiere die Einstellungen
            }
            R.id.pop_up_menu_end -> {
                val alertDialog = createEndDialog(context,callback)
                alertDialog.show()
            }
        }
    }

    fun createEndDialog(context: Context,callback: PopupMenuCallback): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle("Beenden")
            .setMessage("App wirklich Beenden?")
            .setIcon(R.drawable.ic_baseline_exit_to_app_24)
            .setCancelable(true)
            .setNegativeButton("Nein") { _, _ ->
                callback.navigateToSelf()
            }
            .setPositiveButton("Ja") { _, _ ->
                (context as? Activity)?.finish()
            }
            .create()
    }

    interface PopupMenuCallback {
        fun navigateToHome()
        fun navigateToSelf()
    }
}