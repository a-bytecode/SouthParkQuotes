package com.example.southparkquotes.model

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.southparkquotes.R
import com.example.southparkquotes.local.SPDatabase
import kotlinx.coroutines.*
import com.example.southparkquotes.remote.Repository
import com.example.southparkquotes.remote.SouthParkApiServiceQNumber
 
enum class ApiStatus { START, LOADING, ERROR }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val database = SPDatabase.createInstance(application)

    var api = SouthParkApiServiceQNumber.UserApi

    var repo = Repository(api,database)

    val selectedBackground = MutableLiveData<Int>()

    private var _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus : LiveData<ApiStatus>
        get() = _apiStatus

    val characterNameLiveData = repo.selectedCharacterNameEntity // Aktualisierung des Namens durch Mutable Live Data

    var selectedImageResource: Int = R.drawable.stan_marsh_0 // Hier Standardwert einsetzen

    var currentQuoteIndex = 0 // Für das vor-Schalten der Quotes.

    var currentIndex = 0 // Der current Index für das Auswählen des Wallpapers

    var amIfromQuotesMenu = true // Für das erkennen der Navigation zwischen QuotesMenu und Detail

    fun toggleAmIfromQuotesMenu() {
        amIfromQuotesMenu = !amIfromQuotesMenu
    }

    fun throwRandomCharacter(charList : MutableList<Character>) : Character {

        var getRandomChar = charList.random()

        return getRandomChar
    }

    // TODO: Funktion für Anpassung des Character Images 326dp & 460dp
    fun scaleImage(image : ImageView, newWidthDp: Int, newHeightDp: Int, context: Context) {
        val newWidthInPixels = (newWidthDp * context.resources.displayMetrics.density).toInt()
        val newHeightInPixels = (newHeightDp * context.resources.displayMetrics.density).toInt()

        val layoutParams = image.layoutParams
        layoutParams.width = newWidthInPixels
        layoutParams.height = newHeightInPixels
        image.layoutParams = layoutParams
        Log.d("ERIC", "${image.tag} -> Aktuelles Image")
    }

    fun updateImageDimensions(image:ImageView,context: Context) {
        val isEricCartmanImage = image.tag == repo.cartman.imageResource.toString()

        val newWidthDp = if (isEricCartmanImage) 388 else 326
        val newHeightDp = if (isEricCartmanImage) 531 else 460
         if(isEricCartmanImage) {
             scaleImage(image,newWidthDp,newHeightDp,context)
         }
    }

    fun getNextQuote(quoteTextView: TextView): String? {
        val quotesList = characterNameLiveData.value

        if (quotesList != null && quotesList.isNotEmpty()) {
            if (currentQuoteIndex < quotesList.size - 1) {
                currentQuoteIndex++
            } else {
                currentQuoteIndex = 0 // Wenn das Ende erreicht ist, setze auf 0 zurück
            }

            val nextQuote = quotesList[currentQuoteIndex].quote
            quoteTextView.text = nextQuote // Aktualisiere die TextView mit dem nächsten Zitat

            Log.d("GETQUOTES002", "${currentQuoteIndex}")
            return nextQuote
        }

        return null
    }


    fun switchCharactersLeft(imageView: ImageView, myTextView: TextView) {

        repo.charPick = (repo.charPick +1) % repo.charList.size

        repo.charList[repo.charPick].imageResource?.let {
            imageView.setImageResource(it)
            selectedImageResource = it
            val characterName = repo.charList[repo.charPick].name
            updateCharacterName(characterName,myTextView)
            getQuotesResponse(characterName)
        }
     }

    fun switchCharactersRight(imageView: ImageView, myTextView: TextView) {

        repo.charPick = (repo.charPick - 1 + repo.charList.size) % repo.charList.size

        repo.charList[repo.charPick].imageResource?.let {
            imageView.setImageResource(it)
            selectedImageResource = it
            val characterName = repo.charList[repo.charPick].name
            updateCharacterName(characterName,myTextView)
            getQuotesResponse(characterName)
          }
     }

    fun updateCharacterName(name: String, myTextView: TextView) {

            for (character in repo.charList) {
                if (character.name == name) {
                    myTextView.text = character.name
                    break
                }
            }
        }

    fun addNumber(editNumber: EditText) {

        val currentNumber = editNumber.text.toString().toIntOrNull() ?: 1 // Elvis-Operator

        val newNumber = currentNumber +1

        if (newNumber >= 5) {
            editNumber.setText((5).toString())
        } else {
            editNumber.setText(newNumber.toString())
        }
    }

    fun subNumber(editNumber: EditText) {

        val currentNumber = editNumber.text.toString().toIntOrNull() ?: 1 // Elvis-Operator

        val newNumber = currentNumber -1

        if (newNumber <= 0) {
            editNumber.setText((0).toString())
        } else {
            editNumber.setText(newNumber.toString())
        }
    }

    fun switchFwdPic(pictureList: MutableList<Int>,currentImage: ImageView, currentTextView : TextView) {

        currentIndex ++

        if(currentIndex >= pictureList.size) {
            currentIndex = 0 // Hier wird das letzte Element der Liste wieder auf 0 gesetzt.
            Log.d("currentIndex", "CurrentIndex is -> ${pictureList[currentIndex]}")
        }

        val currentBackground = pictureList[currentIndex]

        val backgroundName = getBackgroundName(currentBackground,currentTextView)

        currentImage.setImageResource(currentBackground)

        currentTextView.setText(currentBackground)

        currentTextView.text = backgroundName

    }

    fun getBackgroundName(backgroundResource: Int, currentImage : TextView): String {
        return currentImage.context.resources.getResourceEntryName(backgroundResource)
    }

    fun switchBwdPic(pictureList: MutableList<Int>,currentImage: ImageView, currentTextView : TextView) {

        currentIndex --

        if(currentIndex < 0) {
            currentIndex = pictureList.size -1 // Hier wird das letzte Element der Liste wieder auf 0 gesetzt.
            Log.d("currentIndex", "CurrentIndex is -> ${pictureList[currentIndex]}")
        }

        val currentBackground = pictureList[currentIndex]

        val backgroundName = getBackgroundName(currentBackground,currentTextView)

        currentImage.setImageResource(currentBackground)

        currentTextView.setText(currentBackground)

        currentTextView.text = backgroundName

    }

    fun anmitateTextView(inputText: TextView) {

        val text = inputText.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                animateText(inputText, text)
                delay(1000)
                clearText(inputText, text)
                delay(1000)
            }
        }
    }

    suspend fun animateText(textView: TextView, text: String) {
        val stringBuilder = StringBuilder()

        for (char in text) {
            stringBuilder.append(char)
            withContext(Dispatchers.Main) {
                textView.text = stringBuilder.toString()
            }
            delay(300)
        }
    }

    suspend fun clearText(textView: TextView, text: String) {
        for (i in text.length downTo 0) {
            val subString = text.substring(0, i)
            withContext(Dispatchers.Main) {
                textView.text = subString
            }
            delay(100)
        }
        withContext(Dispatchers.Main) {
            textView.text = "" // Text löschen
        }
    }


    fun setVisible(input: ImageView) {
        input.visibility = View.VISIBLE
    }

    fun setGone(input: ImageView) {
        input.visibility = View.GONE
    }

    fun getQuotesResponse(name: String) {
        viewModelScope.launch {
            try {
                _apiStatus.value = ApiStatus.LOADING
                repo.getQuotesResponse(name)
                Log.d("GETQUOTES001", "${name}")
                _apiStatus.value = ApiStatus.START
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
                Log.d("GETQUOTES001", "${characterNameLiveData.value}")
            }
        }
    }


    fun createEndDialog(context: Context,callback: () -> Unit): AlertDialog {
        Log.d("MyApp", "createEndDialog: Start")
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Beenden")
            .setMessage("App wirklich Beenden?")
            .setIcon(R.drawable.ic_baseline_exit_to_app_24)
            .setCancelable(true)
            .setNegativeButton("Nein") { _, _ ->
                Log.d("MyApp", "createEndDialog: Nein geklickt")
                callback()
            }
            .setPositiveButton("Ja") { _, _ ->
                Log.d("MyApp", "createEndDialog: Ja geklickt")
                (context as? Activity)?.finish()
            }
            .create()
        Log.d("MyApp", "createEndDialog: End")
        alertDialog.show()
        return alertDialog
    }

}