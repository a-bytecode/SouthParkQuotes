package UI

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.DetailquoteFragmentBinding
import model.MainViewModel

class DetailQuoteFragment : Fragment(), MainViewModel.PopupMenuCallback  {

    private lateinit var binding: DetailquoteFragmentBinding

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        viewModel.popupMenuCallback = this

        if (imageResource != 0) {
            binding.charPic01detail.setImageResource(imageResource)
        }

        viewModel.getQuotesNumber("1")

        binding.charPic01detail.setOnClickListener {
            viewModel.getQuotesNumber("1")
        }

        viewModel.charListRequest.observe(viewLifecycleOwner) { charList ->
            val spQuote = charList[0].quote
            binding.detailSPQuote.text = spQuote
            Log.d("CharTest001","${charList[0].name}")
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