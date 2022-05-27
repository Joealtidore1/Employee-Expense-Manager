package com.impactech.expenser

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.impactech.expenser.databinding.ActivityHomeBinding
import com.impactech.expenser.presentation.states_and_events.ExpenseEvents
import com.impactech.expenser.presentation.view_models.ExpenserViewModel
import com.impactech.expenser.utility.*
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: ExpenserViewModel by viewModels()
    private lateinit var navController: NavController

    var add: AppCompatImageView? = null
    var profile: CircleImageView? = null
    var title: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val host = supportFragmentManager.findFragmentById(binding.container.id) as NavHostFragment
        navController = host.navController
        navController.setGraph(R.navigation.nav)
        title = binding.title


        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUpOrFinish()
    }

    private fun navigateUpOrFinish(): Boolean {
        return if (navController.navigateUp()) {
            true
        } else {
            finish()
            true
        }
    }

    override fun onBackPressed() {
        navigateUpOrFinish()
    }

    override fun onNavigateUp(): Boolean {
        return navigateUpOrFinish()
    }

    private fun init(){

        navController.addOnDestinationChangedListener{ _, dest, _ ->
            binding.backHome.show()
            binding.filter.hide()
            when(dest.id){
                R.id.homeFragment -> {
                    binding.filter.show()
                    binding.backHome.hide()
                }
                R.id.addExpenseFragment -> {
                    binding.title.text = "Expense"
                }
                R.id.action_homeFragment_to_createEmployeeFragment ->{
                    binding.title.text = "Add Employee"
                }
            }
        }
        binding.filter.setOnClickListener {
            if(binding.filterContainer.isVisible()){
                binding.filterContainer.hide()
            }else{
                binding.filterContainer.show()
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
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it)
                binding.merchant.setAdapter(adapter)


            }
        }

        binding.merchant.setOnFocusChangeListener {_, b ->
            if(b) {
                isSelected = false
                binding.merchant.showDropDown()
            }
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

        binding.backHome.setOnClickListener {
            navController.navigateUp()
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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


}