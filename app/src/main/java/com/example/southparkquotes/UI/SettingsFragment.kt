package com.example.southparkquotes.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.southparkquotes.databinding.SettingsScreenBinding
import com.example.southparkquotes.model.MainViewModel
import com.example.southparkquotes.remote.Repository

class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsScreenBinding

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var repo : Repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repo = viewModel.repo
        binding = SettingsScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Hier wird das anfangs-Bild aktualisiert.
        binding.backgroundIV.setImageResource(
            viewModel.repo.backgroundPictureList[viewModel.currentIndex]
        )
        // hier wird beim eintritt des Frames der Text aktualisiert.
        binding.bGNameTV.text =
            viewModel.getBackgroundName(
                viewModel.repo.backgroundPictureList[viewModel.currentIndex],
            binding.bGNameTV
        )

        binding.fwIVBtng.setOnClickListener {
            // Forward Click
            viewModel.switchFwdPic(
                viewModel.repo.backgroundPictureList,
                binding.backgroundIV,
                binding.bGNameTV
            )
        }

        binding.backIVBtng.setOnClickListener {
            //Backward Click
            viewModel.switchBwdPic(
                viewModel.repo.backgroundPictureList,
                binding.backgroundIV,
                binding.bGNameTV
            )
        }

        binding.applyBtng.setOnClickListener {
            // TODO: Übernahme des Hintergrundes im DetailquoteFragment
            val selectedBackground =
                viewModel.repo.backgroundPictureList[viewModel.currentIndex]
            viewModel.selectedBackground.value = selectedBackground
            val spec = Toast.LENGTH_SHORT
            Toast.makeText(context,"Background Changed",spec).show()
            // Durch popBackStack() kehren wir auf den vorherigen Screen zurück.
            findNavController().popBackStack()
        }
    }
}
