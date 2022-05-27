package com.impactech.expenser.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.impactech.expenser.HomeActivity
import com.impactech.expenser.R
import com.impactech.expenser.databinding.ExpenseFragmentBinding
import com.impactech.expenser.databinding.FragmentHomeBinding
import com.impactech.expenser.domain.model.Expense
import com.impactech.expenser.presentation.screen.adapter.ExpenseAdapter
import com.impactech.expenser.presentation.view_models.ExpenserViewModel
import java.text.NumberFormat


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ExpenserViewModel by activityViewModels()
    var data = mutableListOf<Expense>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        if(view != null) {
            val viewGroup = view!!.parent as ViewGroup?
            viewGroup?.removeAllViews()
        }
        binding.root.parent.let {
            (it as ViewGroup).removeView(binding.root)
        }
        super.onDestroyView()
    }



    private fun init() {
        val adapter = ExpenseAdapter(data){
            val action = HomeFragmentDirections.actionHomeFragment2ToAddExpenseFragment2()
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

        (requireActivity() as HomeActivity).add?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToAddExpenseFragment2()
            findNavController().navigate(action)
        }
    }

}