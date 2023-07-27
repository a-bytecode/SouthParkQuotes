package model

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

    fun switchCharaktersRight(imageView: ImageView) {

        repo.charPick = (repo.charPick - 1 + repo.charList.size) % repo.charList.size

        imageView.setImageResource(repo.charList[repo.charPick].imageResource)

        characterNameLiveData.value = repo.charList[repo.charPick].name
    }

    fun updateCharacterName(newName: String) {
        // TODO:
        for (charakter in repo.charList) {

            if (charakter.name == newName) {
                characterNameLiveData.value = newName
                break
            }
        }
    }
}