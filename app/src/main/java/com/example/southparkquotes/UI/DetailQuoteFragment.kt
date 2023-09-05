package com.example.southparkquotes.UI

import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

    override fun onResume() {
        super.onResume()

        // Leeren Sie den Text in Ihrer TextView
        binding.detailSPQuote.text = ""

        // Blenden Sie die CardView aus
        binding.cardViewDetail.visibility = View.GONE
        binding.mrHanky.alpha = 1f
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = DetailQuoteFragmentArgs.fromBundle(requireArguments())
        val imageResource = args.imageID
        val imageName = args.characterName

        var firstQuoteLoaded = false

        // TODO: Animation für Mr. Hanky als Ladebild einfügen.
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.circle_animation)

        if (imageResource != 0) {
            binding.charPic01detail.setImageResource(imageResource)
        }

        binding.mrHanky.alpha = 1f
        binding.mrHanky.startAnimation(animation)

        viewModel.apiStatus.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.LOADING -> {
                    binding.cardViewDetail.visibility = View.INVISIBLE
                    binding.mrHanky.visibility = View.GONE
                }
                ApiStatus.START -> {
                    binding.cardViewDetail.visibility = View.VISIBLE
                    binding.mrHanky.alpha = 0f
                }
                ApiStatus.ERROR -> {
                    binding.cardViewDetail.visibility = View.GONE
                    binding.mrHanky.alpha = 0f
                 }
            }
        }

        binding.charPic01detail.setOnClickListener {
            viewModel.getQuotesResponse(imageName.lowercase())
            val nextQuote = viewModel.getNextQuote(binding.detailSPQuote)
            if (nextQuote != null) {
                binding.detailSPQuote.text = nextQuote
                binding.cardViewDetail.alpha = 1f // Setzen Sie die CardView auf sichtbar
                binding.mrHanky.alpha = 0f
            }
            Log.d("imageName001", "${imageName}")
        }

        viewModel.characterNameLiveData.observe(viewLifecycleOwner) { quotesList ->
            if (!firstQuoteLoaded && quotesList.isNotEmpty()) {
                binding.detailSPQuote.text = quotesList[0].quote
                binding.mrHanky.alpha = 0f
                // Der erste Quote wird angezeigt
                firstQuoteLoaded = true
                Log.d("imageName004", "${quotesList[0].name}")
            }
        }
    }
}