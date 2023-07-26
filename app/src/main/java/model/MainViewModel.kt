package model

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.southparkquotes.R
import org.w3c.dom.Text

class MainViewModel : ViewModel() {

    val characterNameLiveData = MutableLiveData<String>() // Aktualisierung des Namens durch Mutable Live Data

    var charPick = 0

    var stan = Charakter("Stan", R.drawable.stan_marsh_0, "Lets Rock it.")
    var cartman = Charakter("Cartman", R.drawable.eric_cartman, "Lets Rock it.")
    var kyle = Charakter("Kyle", R.drawable.kyle_broflovski, "Lets Rock it.")
    var butters = Charakter("Butters", R.drawable.buttersstotch, "Lets Rock it.")

    var charList = mutableListOf(stan,kyle,butters,cartman)

    fun switchCharactersLeft(imageView: ImageView) {

        charPick = (charPick +1) % charList.size

        imageView.setImageResource(charList[charPick].imageResource)

        characterNameLiveData.value = charList[charPick].name
    }

    fun switchCharaktersRight(imageView: ImageView) {

        charPick = (charPick - 1 + charList.size) % charList.size

        imageView.setImageResource(charList[charPick].imageResource)

        characterNameLiveData.value = charList[charPick].name

    }

    fun updateCharacterName(newName: String) {
        // TODO:
        for (charakter in charList) {

            if (charakter.name == newName) {
                characterNameLiveData.value = newName
                break
            }
        }
    }
}