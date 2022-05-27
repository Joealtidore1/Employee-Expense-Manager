package com.impactech.expenser.presentation.screen

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.impactech.expenser.R
import com.impactech.expenser.databinding.FragmentAddExpenseBinding
import com.impactech.expenser.domain.model.Expense
import com.impactech.expenser.presentation.states_and_events.ExpenseEvents
import com.impactech.expenser.presentation.view_models.ExpenserViewModel
import com.impactech.expenser.utility.*
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseFragment : Fragment() {
    private lateinit var actionIntent: Intent
    private var imageUri: String? = null
    private val navArgs: AddExpenseFragmentArgs by navArgs()
    private val viewModel: ExpenserViewModel by activityViewModels()
    private lateinit var binding: FragmentAddExpenseBinding

    private var imagePath: String = ""

    private val permission: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result: Boolean ->
        if (result) {
            activityResult.launch(actionIntent)
        } else {
            bottomSheet("Permission denied", "Camera permission denied")
        }
    }

    private val activityResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                if (intent != null) {
                    val imageUri = intent.data
                    val file = getRealPathFromURI(imageUri)
                    imagePath = file!!
                    val photo: Bitmap = BitmapFactory.decodeFile(file)
                    binding.receipt.setImageBitmap(photo)
                    binding.receipt.show()

                }
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
        init()
    }

    private fun init() {
        viewModel.merchants.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, it)
                binding.expenseType.setAdapter(adapter)
            }
        }

        navArgs.expense?.let {
            binding.apply {
                expenseType.setText(it.merchant)
                amount.setText(it.total.toString())
                expenseDate.text = it.date
                comments.setText(it.comment)
                if(it.receiptPath.isNotEmpty()){
                    receipt.setImageBitmap(BitmapFactory.decodeFile(it.receiptPath))
                    receipt.show()
                }
                this.saveExpense.text = "Update"
            }
        }

        binding.expenseType.setOnClickListener {
            binding.expenseType.showDropDown()
        }

        binding.expenseDate.setOnClickListener {
            setDate(binding.expenseDate)
        }
        binding.selectReceipt.setOnClickListener {
            actionIntent = Intent(Intent.ACTION_PICK)
            actionIntent.type = "image/*"
            if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permission.launch(Manifest.permission.CAMERA)
            } else {
                activityResult.launch(actionIntent)
            }
        }

        binding.saveExpense.setOnClickListener {

            if(!binding.expenseType.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Merchant"))
                return@setOnClickListener
            }

            if(!binding.amount.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Amount"))
                return@setOnClickListener
            }

            if(!binding.expenseDate.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Date"))
                return@setOnClickListener
            }

            if(!binding.comments.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Comment"))
                return@setOnClickListener
            }


            if(navArgs.expense == null){
                val expense = Expense()

                expense.apply {
                    date = binding.expenseDate.extractText()
                    total = binding.amount.extractText().toDouble()
                    merchant = binding.expenseType.extractText()
                    status = if(imagePath.isEmpty())
                        "New" else "In Progress"
                    comment = binding.comments.extractText()
                    receiptPath = imagePath?:""
                }
                viewModel.onEvent(ExpenseEvents.AddExpense(expense))
            }else{
                navArgs.expense?.apply {
                    date = binding.expenseDate.extractText()
                    total = binding.amount.extractText().toDouble()
                    merchant = binding.expenseType.extractText()
                    status = if(imagePath.isEmpty())
                        "New" else "In Progress"
                    comment = binding.comments.extractText()
                    receiptPath = imagePath
                    viewModel.onEvent(ExpenseEvents.UpdateExpense(this))
                }

            }
            viewModel.onEvent(ExpenseEvents.Filter)
            findNavController().navigateUp()
        }
    }

    private fun setDate(view: TextView){
        val myCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerDialogTheme,
            { _: DatePicker?, year: Int, month: Int, day: Int ->
                val format = "MM/dd/yyyy"
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                val currentDate = SimpleDateFormat(
                    format,
                    Locale.getDefault()
                ).format(myCalendar.time)

                view.text = currentDate


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

    private fun Fragment.getRealPathFromURI(contentURI: Uri?): String? {
        val result: String?
        val cursor: Cursor? = requireActivity().contentResolver.query(contentURI?:return null, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

}