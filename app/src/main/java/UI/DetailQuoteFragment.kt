package UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.southparkquotes.databinding.DetailquoteFragmentBinding
import model.MainViewModel

class DetailQuoteFragment : Fragment() {

    private lateinit var binding: DetailquoteFragmentBinding

    private val viewModel : MainViewModel by activityViewModels()

    private val args: DetailQuoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DetailquoteFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args = DetailQuoteFragmentArgs.fromBundle(requireArguments())
        val imageResource = args.imageID

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
        }

    }
}