package com.udacity.shoestore.screens.detail

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.ShoeStoreViewModel
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding

/**
 * Created by Jorge Alberto Gonzalez
 * */
class ShoeDetailFragment : Fragment() {
    private lateinit var binding: FragmentShoeDetailBinding
    private val viewModel: ShoeStoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)
        binding.viewModel = viewModel
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        // Save button disabled by default
        binding.saveButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccentDisabled))

        // Cancel button click event
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        // Enable/disable the save button
        binding.nameText.doAfterTextChanged {
            if (it.toString().isEmpty()) {
                binding.saveButton.isEnabled = false
                binding.saveButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccentDisabled))

            } else {
                binding.saveButton.isEnabled = true
                binding.saveButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            }
        }

        // Observe the shoe added event
        viewModel.shoeAddedEvent.observe(viewLifecycleOwner, { shoeAdded ->
            if (shoeAdded) {
                viewModel.onShoeAddedComplete()

                // Navigate back to the list
                findNavController().popBackStack()
            }
        })
    }
}
