package com.impactech.expenser.presentation.screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.impactech.expenser.HomeActivity
import com.impactech.expenser.R
import com.impactech.expenser.databinding.ExpenseFragmentBinding
import com.impactech.expenser.domain.model.Expense
import com.impactech.expenser.presentation.screen.adapter.ExpenseAdapter
import com.impactech.expenser.presentation.view_models.ExpenserViewModel
import com.impactech.expenser.utility.hide
import com.impactech.expenser.utility.isVisible
import com.impactech.expenser.utility.show
import java.text.NumberFormat


class HomeFragment : Fragment() {
    private lateinit var binding: ExpenseFragmentBinding
    private val viewModel: ExpenserViewModel by activityViewModels()
    var data = mutableListOf<Expense>()

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
        val adapter = ExpenseAdapter(data){
            val action = HomeFragmentDirections.actionHomeFragmentToAddExpenseFragment(it)
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        binding.apply {
            viewModel.apply {
                expenses.observe(viewLifecycleOwner){
                    data.clear()
                    data.addAll(it)
                    adapter.notifyDataSetChanged()
                }

                amount.observe(viewLifecycleOwner){

                    reimburse.text = NumberFormat.getCurrencyInstance().format(it?:0.0)
                }
            }
        }



        binding.add.setOnClickListener {
            binding.options.show()
            binding.add.hide()
        }

        binding.root.setOnClickListener {
            if(binding.options.isVisible()){
                binding.options.hide()
                binding.add.show()
            }
        }

        binding.recyclerView.setOnTouchListener { _, _ ->
            binding.root.performClick()
        }

        binding.expense.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddExpenseFragment()
            findNavController().navigate(action)
        }

        binding.employee.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreateEmployeeFragment()
            findNavController().navigate(action)
        }
    }

}