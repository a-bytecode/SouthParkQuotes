package UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.southparkquotes.databinding.DetailquoteFragmentBinding
import model.MainViewModel

class DetailQuoteFragment : Fragment() {

    private lateinit var binding: DetailquoteFragmentBinding

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DetailquoteFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.detailSPQuote.setOnClickListener {
            viewModel.getQuotesNumber("1")
        }

        viewModel.charListRequest.observe(viewLifecycleOwner) { charList ->
            val spQuote = charList[0].quote
            binding.detailSPQuote.text = spQuote
        }
    }
}