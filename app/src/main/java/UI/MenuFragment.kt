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

        var stanImageID = view.findViewById<ImageView>(R.id.characterIV)

        var stan : Charakter = Charakter("Stan",R.drawable.stan_marsh_0, "Lets Rock it.")

        binding.leftIV.setOnClickListener {


        }
    }

}