package UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.HomeFragmentBinding
import com.example.southparkquotes.databinding.MenuFragmentBinding
import model.Charakter
import model.MainViewModel

class MenuFragment : Fragment() {

    private lateinit var binding: MenuFragmentBinding

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MenuFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var charPick = 0

        val stanImageView = view.findViewById<ImageView>(R.id.characterIV)

        var stan = Charakter("Stan",R.drawable.stan_marsh_0, "Lets Rock it.")
        var cartman = Charakter("Stan",R.drawable.eric_cartman, "Lets Rock it.")
        var kyle = Charakter("Stan",R.drawable.kyle_broflovski, "Lets Rock it.")
        var butters = Charakter("Stan",R.drawable.buttersstotch, "Lets Rock it.")

        var charList = mutableListOf(stan,kyle,butters,cartman)

        stanImageView.setImageResource(stan.imageResource)

        binding.leftIV.setOnClickListener {

            charPick = (charPick +1) % charList.size

            stanImageView.setImageResource(charList[charPick].imageResource)

        }

        binding.rightIV.setOnClickListener {

            charPick = (charPick + 1) % charList.size

            stanImageView.setImageResource(charList[charPick].imageResource)

        }
    }

}