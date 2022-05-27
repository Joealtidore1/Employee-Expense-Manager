package com.impactech.expenser.presentation.screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.impactech.expenser.R
import com.impactech.expenser.databinding.FragmentCreateEmployeeBinding
import com.impactech.expenser.domain.model.Employee
import com.impactech.expenser.presentation.states_and_events.AuthEvent
import com.impactech.expenser.presentation.view_models.AuthViewModel
import com.impactech.expenser.utility.bottomSheet
import com.impactech.expenser.utility.extractText
import com.impactech.expenser.utility.toast
import com.impactech.expenser.utility.validate


class CreateEmployeeFragment : Fragment() {
    private var imagePath: String = ""
    private lateinit var actionIntent: Intent
    private lateinit var binding: FragmentCreateEmployeeBinding

    private val viewModel: AuthViewModel by activityViewModels()

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
                    binding.imageView.setImageBitmap(photo)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener {
            actionIntent = Intent(Intent.ACTION_PICK)
            actionIntent.type = "image/*"
            if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permission.launch(Manifest.permission.CAMERA)
            } else {
                activityResult.launch(actionIntent)
            }
        }

        binding.createEmployee.setOnClickListener {
            if(!binding.employeeName.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Name"))
                return@setOnClickListener
            }

            if(!binding.location.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Location"))
                return@setOnClickListener
            }

            if(!binding.department.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Name"))
                return@setOnClickListener
            }

            if(!binding.descriptionN.validate()){
                bottomSheet(getString(R.string.validation_error_message, "Name"))
                return@setOnClickListener
            }

            if(imagePath.isEmpty()){
                bottomSheet("Image is required")
                return@setOnClickListener
            }

            val employee = Employee()

            employee.apply {
                name = binding.employeeName.extractText()
                profilePicture = imagePath
                location = binding.location.extractText()
                department = binding.department.extractText()
                jobDescription = binding.descriptionN.extractText()
                username = binding.employeeName.extractText()
                password = binding.employeeName.extractText()
            }
            viewModel.onEvent(AuthEvent.Register(employee))
            val action = CreateEmployeeFragmentDirections.actionCreateEmployeeFragmentToEmployeeProfileFragment2(employee)
            findNavController().navigate(action)
        }
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