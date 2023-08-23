package com.example.southparkquotes.UI

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

class DetailQuoteFragment : Fragment(), MainViewModel.PopupMenuCallback  {

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

    override fun navigateToHome() {
        findNavController().navigate(DetailQuoteFragmentDirections.actionDetailQuoteFragmentToHomeFragment())
    }

    override fun navigateToSelf() {
        findNavController().navigate(DetailQuoteFragmentDirections.actionDetailQuoteFragmentSelf(
            viewModel.selectedImageResource,viewModel.selectedImageResource.toString())
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = DetailQuoteFragmentArgs.fromBundle(requireArguments())
        val imageResource = args.imageID
        val imageName = args.characterName

        viewModel.popupMenuCallback = this

        var firstQuoteLoaded = false

        // TODO: Animation für Mr. Hanky als Ladebild einfügen.
        val animation = AnimationUtils.loadAnimation(requireContext(),R.anim.circle_animation)

        if (imageResource != 0) {
            binding.charPic01detail.setImageResource(imageResource)
        }

        viewModel.getQuotesResponse(imageName.lowercase())
        Log.d("CharTest002","${imageName}")

        viewModel.apiStatus.observe(viewLifecycleOwner) {
            when(it) {
                ApiStatus.LOADING -> {
                    binding.cardViewDetail.visibility = View.VISIBLE
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
            Log.d("imageName001","${imageName}")
        }

        viewModel.characterNameLiveData.observe(viewLifecycleOwner) { quotesList ->
            if (!firstQuoteLoaded && quotesList.isNotEmpty()) {
                binding.detailSPQuote.text = quotesList[0].quote // Zeige den ersten Quote an
                firstQuoteLoaded = true
                Log.d("imageName004","${quotesList[0].name}")
            }
        }

        binding.menubtng.setOnClickListener {
            showPopUp(binding.menubtng)
        }
    }

    // Pop up Menu - - - -> Setup <- - - - Pop up Menu //

    @RequiresApi(Build.VERSION_CODES.Q)
    fun showPopUp(view: View) {
        // Überprüfung der Android Version (SDK_INT) Verallgemeinert die Handy SDK
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val wrapper = ContextThemeWrapper(requireContext(), R.style.popupMenuStyle)
            val popupMenu = PopupMenu(wrapper, view)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                viewModel.popupMenuCallback?.let { it1 ->
                    viewModel.handlePopupMenuAction(it.itemId,requireContext(),
                        it1
                    )
                }
                true
            }
            popupMenu.setOnDismissListener {
                // Respond to popup being dismissed.
            }
            // Show the popup menu.
            popupMenu.show()
        } else {

            val wrapper = ContextThemeWrapper(requireContext(), R.style.popupMenuStyle)
            val popupMenu = PopupMenu(wrapper, view)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.menu.findItem(R.id.pop_up_menu_home)
                .setIcon(R.drawable.ic_baseline_home_24)
            popupMenu.menu.findItem(R.id.pop_up_menu_settings)
                .setIcon(R.drawable.ic_baseline_settings_24)
            popupMenu.menu.findItem(R.id.pop_up_menu_end)
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)

            popupMenu.setForceShowIcon(true)

            popupMenu.setOnMenuItemClickListener {
                viewModel.popupMenuCallback?.let { it1 ->
                    viewModel.handlePopupMenuAction(it.itemId,requireContext(),
                        it1
                    )
                }
                true
            }
            popupMenu.setOnDismissListener {
                // Respond to popup being dismissed.
            }
            // Show the popup menu.
            popupMenu.show()
        }
    }
}