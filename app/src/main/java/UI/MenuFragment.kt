package UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.MenuFragmentBinding
import model.MainViewModel
import remote.Repository

class MenuFragment : Fragment() {

    private lateinit var binding: MenuFragmentBinding

    private val viewModel : MainViewModel by activityViewModels()

    private val repo = Repository()

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

        stanImageView.setImageResource(repo.stan.imageResource)

        viewModel.updateCharacterName(repo.charList[repo.charPick].name)

        viewModel.characterNameLiveData.observe(viewLifecycleOwner) { newName ->
            binding.nameTV.text = newName
        }

        binding.leftIV.setOnClickListener {

            viewModel.switchCharactersLeft(stanImageView)

        }

        binding.rightIV.setOnClickListener {

            viewModel.switchCharactersRight(stanImageView)

        }

    }

}