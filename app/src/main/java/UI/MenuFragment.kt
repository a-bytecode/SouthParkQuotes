package UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.MenuFragmentBinding
import model.MainViewModel
import remote.Repository
import remote.SouthParkApiServiceQNumber

class MenuFragment : Fragment() {

    private lateinit var binding: MenuFragmentBinding

    private val viewModel : MainViewModel by activityViewModels()

    private val api = SouthParkApiServiceQNumber.UserApi

    private val repo = Repository(api)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MenuFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // TODO: Tasten Einbinden & Übergabe-Argumente über den Navgraph einstellen.

        val stanImageView = view.findViewById<ImageView>(R.id.characterIV)

        stanImageView.setImageResource(repo.charList[repo.charPick].imageResource)

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

        binding.checkIV.setOnClickListener {

            viewModel.setVisible(binding.check2IV)
            viewModel.setGone(binding.checkIV)

        }

        binding.check2IV.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToQuotesMenuFragment())
            }
        }
    }
