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

    override fun onDestroyView() {
        // Leeren Sie den Text in Ihrer TextView, wenn das Fragment zerstört wird
        binding.detailSPQuote.text = ""

        // Blenden Sie die CardView aus
        binding.cardViewDetail.visibility = View.GONE
        super.onDestroyView()
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

        viewModel.apiStatus.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.LOADING -> {
                    binding.cardViewDetail.visibility = View.INVISIBLE
                }
                ApiStatus.START -> {
                    binding.cardViewDetail.visibility = View.VISIBLE
                }
                ApiStatus.ERROR -> {
                    binding.cardViewDetail.visibility = View.GONE
                }
            }
        }

        binding.charPic01detail.setOnClickListener {
            viewModel.getQuotesResponse(imageName.lowercase())
            val nextQuote = viewModel.getNextQuote(binding.detailSPQuote)
            if (nextQuote != null) {
                binding.detailSPQuote.text = nextQuote
            }
            Log.d("imageName001", "${imageName}")
        }

        viewModel.characterNameLiveData.observe(viewLifecycleOwner) { quotesList ->
            if (!firstQuoteLoaded && quotesList.isNotEmpty()) {
                binding.detailSPQuote.text = quotesList[0].quote // Zeige den ersten Quote an
                firstQuoteLoaded = true
                Log.d("imageName004", "${quotesList[0].name}")
            }
        }
    }
}