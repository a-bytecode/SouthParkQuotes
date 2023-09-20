package com.example.southparkquotes.UI

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.DetailquoteFragmentBinding
import com.example.southparkquotes.model.ApiStatus
import com.example.southparkquotes.model.MainViewModel
import com.example.southparkquotes.remote.Repository

class DetailQuoteFragment : Fragment() {

    private lateinit var binding: DetailquoteFragmentBinding

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var repo : Repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repo = viewModel.repo
        binding = DetailquoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() { // on Resume wird verwendet um Aktionen
        // bei der wiederherstellung des Frames zu aktivieren.
        super.onResume()
        binding.cardViewDetail.visibility = View.INVISIBLE
        binding.mrHanky.alpha = 1f
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val args = DetailQuoteFragmentArgs.fromBundle(requireArguments())
        val imageResource = args.imageID
        val imageName = args.character.name
        val chosenCharacter = args.character

        var firstQuoteLoaded = false
        // animation für das ImageView
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.circle_animation)

        if (viewModel.amIfromQuotesMenu) {

            val initialCharacter = viewModel.throwRandomCharacter(viewModel.repo.charList)
            binding.charPic01detail.setImageResource(initialCharacter.imageResource!!)

            binding.charPic01detail.setOnClickListener {

                viewModel.playVoices(initialCharacter,requireContext())
                viewModel.getQuotesResponse(initialCharacter.name.lowercase())

                val nextQuote = viewModel.getNextQuote(binding.detailSPQuote)

                if (nextQuote != null) {
                    binding.detailSPQuote.text = nextQuote
                }
                Log.d("fromMQuoteMenu", "${initialCharacter.name.lowercase()}")

                viewModel.characterNameLiveData.observe(viewLifecycleOwner) { quotesList ->
                    if (!firstQuoteLoaded && quotesList.isNotEmpty()) {
                        binding.detailSPQuote.text = quotesList[0].quote
                        firstQuoteLoaded = true
                        Log.d("fromMQuoteMenu", "${quotesList[0].quote}")
                    }
                }
            }

        } else {
            if (imageResource != 0) {
                binding.charPic01detail.setImageResource(imageResource)
            }

            binding.charPic01detail.setOnClickListener {
                viewModel.getQuotesResponse(imageName.lowercase())
                viewModel.playVoices(chosenCharacter,requireContext())
                val nextQuote = viewModel.getNextQuote(binding.detailSPQuote)
                if (nextQuote != null) {
                    binding.detailSPQuote.text = nextQuote
                }
                Log.d("imageName001", "${imageName}")
            }

            viewModel.characterNameLiveData.observe(viewLifecycleOwner) { quotesList ->
                if (!firstQuoteLoaded && quotesList.isNotEmpty()) {
                    binding.detailSPQuote.text = quotesList[0].quote
                    firstQuoteLoaded = true
                    Log.d("imageName004", "${quotesList[0].quote}")
                }
            }
        }


        binding.mrHanky.startAnimation(animation)

        viewModel.apiStatus.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.LOADING -> {
                    binding.cardViewDetail.visibility = View.INVISIBLE
                    binding.mrHanky.alpha = 1f
                }
                ApiStatus.START -> {
                    binding.cardViewDetail.visibility = View.VISIBLE
                    binding.mrHanky.alpha = 0f
                }
                ApiStatus.ERROR -> {
                    binding.cardViewDetail.visibility = View.INVISIBLE
                    binding.mrHanky.alpha = 0f
                 }
            }
        }

        viewModel.selectedBackground.observe(viewLifecycleOwner, Observer { bkgResource ->
            binding.backgroundIV.setImageResource(bkgResource.resourceID)
        })
    }
}