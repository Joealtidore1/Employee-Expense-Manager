package com.impactech.expenser

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.impactech.expenser.databinding.ActivityHomeBinding
import com.impactech.expenser.presentation.states_and_events.ExpenseEvents
import com.impactech.expenser.presentation.view_models.ExpenserViewModel
import com.impactech.expenser.utility.extractText
import com.impactech.expenser.utility.hide
import com.impactech.expenser.utility.isVisible
import com.impactech.expenser.utility.show
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: ExpenserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.filter.setOnClickListener {
            if(binding.filterContainer.isVisible()){
                binding.filterContainer.show()
            }else{
                binding.filterContainer.hide()
            }
        }

        binding.fromDate.setOnClickListener {
            setDate(binding.fromDate)
        }

        binding.fromDate.addTextChangedListener {
            viewModel.state = viewModel.state.copy(
                minDate = it.toString()
            )
            viewModel.onEvent(ExpenseEvents.Filter)
        }

        binding.toDate.setOnClickListener {
            setDate(binding.toDate)
        }

        binding.toDate.addTextChangedListener {
            viewModel.state = viewModel.state.copy(
                maxDate = it.toString()
            )
            viewModel.onEvent(ExpenseEvents.Filter)
        }

        binding.minAmount.addTextChangedListener {
            viewModel.state = viewModel.state.copy(
                minTotal = it.toString().toDouble()
            )
            viewModel.onEvent(ExpenseEvents.Filter)
        }

        binding.maxAmount.addTextChangedListener {
            viewModel.state = viewModel.state.copy(
                maxTotal = it.toString().toDouble()
            )
            viewModel.onEvent(ExpenseEvents.Filter)
        }

        var isSelected = false
        viewModel.merchants.observe(this){
            if(it.isNotEmpty()){
                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, it)
                binding.merchant.setAdapter(adapter)


            }
        }

        binding.merchant.setOnFocusChangeListener {_, b ->
            isSelected = false
            binding.merchant.showDropDown()
        }

        binding.merchant.setOnItemClickListener { adapterView, _, i, _ ->
            viewModel.state = viewModel.state.copy(
                merchant = adapterView.getItemAtPosition(i) as String
            )
            viewModel.onEvent(ExpenseEvents.Filter)
            isSelected = true
        }

        binding.merchant.setOnDismissListener {
            if(!isSelected){
                val clear = viewModel.state.merchant.isNullOrBlank() ||  viewModel.state.merchant != binding.merchant.extractText()
                if(clear){
                    binding.merchant.text = null
                }
            }
        }

        binding.newCb.setOnCheckedChangeListener { _, b ->
            viewModel.state = viewModel.state.copy(
                new = b
            )
            viewModel.onEvent(ExpenseEvents.Filter)
        }

        binding.inProgress.setOnCheckedChangeListener { _, b ->
            viewModel.state = viewModel.state.copy(
                inProgress = b
            )
            viewModel.onEvent(ExpenseEvents.Filter)
        }

        binding.reimburse.setOnCheckedChangeListener { _, b ->
            viewModel.state = viewModel.state.copy(
                reimbursed = b
            )
            viewModel.onEvent(ExpenseEvents.Filter)
        }

        binding.clearFilters.setOnClickListener {
            binding.newCb.isChecked = true
            binding.inProgress.isChecked = true
            binding.reimburse.isChecked = true
            binding.fromDate.clearComposingText()
            binding.toDate.clearComposingText()
            binding.minAmount.clearComposingText()
            binding.maxAmount.clearComposingText()
            binding.merchant.clearComposingText()
            viewModel.onEvent(ExpenseEvents.ClearFilter)
        }

        binding.done.setOnClickListener { binding.filter.performClick() }
    }

    private fun setDate(view: EditText){
        val myCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.MyDatePickerDialogTheme,
            { _: DatePicker?, year: Int, month: Int, day: Int ->
                val format = "yyyy-MM-dd"
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                val currentDate = SimpleDateFormat(
                    format,
                    Locale.getDefault()
                ).format(myCalendar.time)

                view.setText(currentDate)


            },
            myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.primary_dark))
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.primary_dark))
    }


}