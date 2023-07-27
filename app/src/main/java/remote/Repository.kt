package remote

import com.example.southparkquotes.R
import model.Charakter

class Repository {


    var charPick = 0

    var stan = Charakter("Stan", R.drawable.stan_marsh_0, "Lets Rock it.")
    var cartman = Charakter("Cartman", R.drawable.eric_cartman, "Lets Rock it.")
    var kyle = Charakter("Kyle", R.drawable.kyle_broflovski, "Lets Rock it.")
    var butters = Charakter("Butters", R.drawable.buttersstotch, "Lets Rock it.")

    var charList = mutableListOf(stan,kyle,butters,cartman)
}