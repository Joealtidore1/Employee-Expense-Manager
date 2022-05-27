package com.impactech.expenser.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.impactech.expenser.R
import com.impactech.expenser.databinding.ExpenseFragmentBinding
import com.impactech.expenser.presentation.view_models.ExpenserViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: ExpenseFragmentBinding
    private val viewModel: ExpenserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExpenseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.apply {
            viewModel.apply {

            }
        }
    }

}