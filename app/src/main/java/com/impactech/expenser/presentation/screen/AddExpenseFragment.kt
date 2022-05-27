package com.impactech.expenser.presentation.screen

import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.impactech.expenser.R
import com.impactech.expenser.databinding.FragmentAddExpenseBinding
import com.impactech.expenser.presentation.view_models.ExpenserViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseFragment : Fragment() {
    //val navArgs: AddExpenseFragmentArgs by navArgs()
    private val viewModel: ExpenserViewModel by activityViewModels()
    private lateinit var binding: FragmentAddExpenseBinding

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext())

            val bitmap = BitmapFactory.decodeFile(uriFilePath)
            binding.receipt.setImageBitmap(bitmap)
        } else {
            // an error occurred
            val exception = result.error
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init()
    }

    private fun init() {

        viewModel.merchants.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, it)
                binding.expenseType.setAdapter(adapter)
            }
        }

        binding.expenseType.setOnClickListener {
            binding.expenseType.showDropDown()
        }

        binding.expenseDate.setOnClickListener {
            setDate(binding.expenseDate)
        }
        binding.selectReceipt.setOnClickListener {
            cropImage.launch(
                options {
                    setImageSource(
                        includeCamera = true,
                        includeGallery = true,
                    )
                    setScaleType(CropImageView.ScaleType.FIT_CENTER)
                    setCropShape(CropImageView.CropShape.RECTANGLE)
                    setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                }
            )
        }
    }

    private fun setDate(view: TextView){
        val myCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
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
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_dark))
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_dark))
    }

}