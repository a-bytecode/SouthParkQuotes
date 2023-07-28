package UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.southparkquotes.R
import com.example.southparkquotes.databinding.QuotesmenuFragmentBinding
import model.MainViewModel
import remote.Repository

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
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       // TODO:
        binding.rightqIV.setOnClickListener {

            viewModel.addNumber(binding.editTextNumber)
        }

        binding.leftqIV.setOnClickListener {
            viewModel.subNumber(binding.editTextNumber)
        }
    }
}