package com.example.southparkquotes.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.MenuFragmentBinding
import com.example.southparkquotes.model.MainViewModel



class MenuFragment : Fragment() {

    private lateinit var binding: MenuFragmentBinding

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MenuFragment", "onCreateView called")

        binding = MenuFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val needImageView = view.findViewById<ImageView>(R.id.characterIV)
        val newWidthDp = 326
        val newHeightDp = 460

//        viewModel.scaleImage(needImageView,newWidthDp,newHeightDp,requireContext())
//
//        viewModel.updateImageDimensions(needImageView,requireContext())

        viewModel.repo.charList[viewModel.repo.charPick].imageResource?.let { needImageView.setImageResource(it) }

        viewModel.updateCharacterName(viewModel.repo.charList[viewModel.repo.charPick].name,binding.nameTV)

        binding.leftIV.setOnClickListener {

            viewModel.switchCharactersLeft(needImageView,binding.nameTV)

        }

        binding.rightIV.setOnClickListener {

            viewModel.switchCharactersRight(needImageView,binding.nameTV)

        }

        binding.checkIV.setOnClickListener {
            viewModel.setVisible(binding.check2IV)
            viewModel.setGone(binding.checkIV)
        }

        binding.check2IV.setOnClickListener {
            val imageResource = viewModel.repo.charList[viewModel.repo.charPick].imageResource
            viewModel.selectedImageResource = imageResource ?: R.drawable.stan_marsh_0
            val selectedCharacter = viewModel.repo.charList[viewModel.repo.charPick]

            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.sp_nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            viewModel.amIfromQuotesMenu = false

            navController.navigate(
                MenuFragmentDirections.actionMenuFragmentToDetailQuoteFragment(
                    viewModel.selectedImageResource, viewModel.amIfromQuotesMenu,selectedCharacter
                )
            )
            Log.d("CharNameMenuMenu", "ImageID: ${imageResource.toString()}")
            Log.d("CharNameMenuMenu", "CharacterName: ${selectedCharacter}")
        }
    }
}