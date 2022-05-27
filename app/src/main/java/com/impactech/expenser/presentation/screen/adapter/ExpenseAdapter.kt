package com.impactech.expenser.presentation.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impactech.expenser.databinding.ActivityHomeBinding
import com.impactech.expenser.databinding.ItemExpenseCellBinding
import com.impactech.expenser.domain.model.Expense
import java.text.NumberFormat
import kotlin.math.exp

class ExpenseAdapter(private val data: List<Expense>, val click: (Expense) -> Unit): RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemExpenseCellBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(expense: Expense){
            binding.let {
                expense.apply {
                    it.merchant.text = merchant
                    it.status.text = status
                    it.total.text = NumberFormat.getCurrencyInstance().format(total)
                    it.date.text = date
                }

                it.root.setOnClickListener {
                    click(expense)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  ItemExpenseCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size
}