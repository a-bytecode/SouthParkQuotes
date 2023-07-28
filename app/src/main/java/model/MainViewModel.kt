package model

import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import remote.Repository

class MainViewModel : ViewModel() {

    private var repo = Repository()

    val characterNameLiveData = MutableLiveData<String>() // Aktualisierung des Namens durch Mutable Live Data

    fun switchCharactersLeft(imageView: ImageView) {

        repo.charPick = (repo.charPick +1) % repo.charList.size

        imageView.setImageResource(repo.charList[repo.charPick].imageResource)

        characterNameLiveData.value = repo.charList[repo.charPick].name
    }

    fun switchCharactersRight(imageView: ImageView) {

        repo.charPick = (repo.charPick - 1 + repo.charList.size) % repo.charList.size

        imageView.setImageResource(repo.charList[repo.charPick].imageResource)

        characterNameLiveData.value = repo.charList[repo.charPick].name
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

        editNumber.setText(newNumber.toString())
    }

    fun subNumber(editNumber: EditText) {

    }
}