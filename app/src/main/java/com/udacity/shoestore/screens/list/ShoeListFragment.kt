package com.udacity.shoestore.screens.list

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.udacity.shoestore.R
import com.udacity.shoestore.ShoeStoreViewModel
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import timber.log.Timber

/**
 * Created by Jorge Alberto Gonzalez
 * */
class ShoeListFragment : Fragment() {
    private lateinit var binding: FragmentShoeListBinding
    private val viewModel: ShoeStoreViewModel by activityViewModels()
    private val params by lazy {
        LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        // Params for adding each shoe item to the list
        params.let {
            params.marginStart = resources.getDimensionPixelOffset(R.dimen.layout_margin)
            params.marginEnd = resources.getDimensionPixelOffset(R.dimen.layout_margin)
            params.topMargin = resources.getDimensionPixelOffset(R.dimen.layout_margin)
        }

        // Observes the shoe list and adds it to the layout
        viewModel.shoeList.observe(viewLifecycleOwner, { shoeList ->
            binding.containerLinearLayout.removeAllViews()
            shoeList.forEach {
                val shoeItemTextView = TextView(requireActivity())
                shoeItemTextView.text = it.name
                shoeItemTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size))
                binding.containerLinearLayout.addView(shoeItemTextView, params)
            }
        })
    }
}
