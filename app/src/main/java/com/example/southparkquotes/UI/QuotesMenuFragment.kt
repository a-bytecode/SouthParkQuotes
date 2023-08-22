package com.example.southparkquotes.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.QuotesmenuFragmentBinding
import com.example.southparkquotes.model.MainViewModel
import com.example.southparkquotes.remote.Repository

class QuotesMenuFragment: Fragment() {

    private lateinit var binding: QuotesmenuFragmentBinding

    private val viewModel : MainViewModel by activityViewModels()

    private lateinit var repo : Repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = QuotesmenuFragmentBinding.inflate(inflater)
        repo = viewModel.repo
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rightqIV.setOnClickListener {
            viewModel.addNumber(binding.editTextNumber)
        }

        binding.leftqIV.setOnClickListener {
            viewModel.subNumber(binding.editTextNumber)
        }

        binding.checkqIV.setOnClickListener {
            viewModel.setGone(binding.checkqIV)
            viewModel.setVisible(binding.check2qIV)
        }

        binding.check2qIV.setOnClickListener {
            val imageResource = repo.charList[repo.charPick].imageResource
            viewModel.selectedImageResource = imageResource ?: R.drawable.stan_marsh_0 // Standardwert einsetzen
            val selectedCharacter = repo.charList[repo.charPick].name
            findNavController().navigate(
                QuotesMenuFragmentDirections.actionQuotesMenuFragmentToDetailQuoteFragment(
                    viewModel.selectedImageResource, selectedCharacter
                )
            )
            Log.d("CharNameMenu",selectedCharacter)
        }
    }
}