package UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.MenuFragmentBinding
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

        val stanImageView = view.findViewById<ImageView>(R.id.characterIV)

        stanImageView.setImageResource(viewModel.stan.imageResource)

        viewModel.updateCharacterName(viewModel.charList[viewModel.charPick].name)

        viewModel.characterNameLiveData.observe(viewLifecycleOwner) { newName ->
            binding.nameTV.text = newName
        }

        binding.leftIV.setOnClickListener {

            viewModel.switchCharactersLeft(stanImageView)


        }

        binding.rightIV.setOnClickListener {

            viewModel.switchCharaktersRight(stanImageView)
        }


    }

}