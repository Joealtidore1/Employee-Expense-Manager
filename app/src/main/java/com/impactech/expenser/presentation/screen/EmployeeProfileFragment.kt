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
import com.impactech.expenser.HomeActivity
import com.impactech.expenser.R
import com.impactech.expenser.databinding.FragmentEmployeeProfileBinding
import com.impactech.expenser.presentation.states_and_events.AuthEvent
import com.impactech.expenser.presentation.view_models.AuthViewModel
import com.impactech.expenser.utility.bottomSheet
import com.impactech.expenser.utility.show


class EmployeeProfileFragment : Fragment() {
    private var imagePath: String = ""
    private lateinit var actionIntent: Intent
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentEmployeeProfileBinding

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
                    binding.profileImage.setImageBitmap(photo)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.onEvent(AuthEvent.GetUserInfo)

        val home = (requireActivity() as HomeActivity)
        viewModel.employee.observe(viewLifecycleOwner){
            it?.let {
                binding.apply {
                    profileName.text = it.username
                    userName.text = it.name
                    locationName.text = it.location
                    departmentName.text = it.department
                    descriptionName.text = it.jobDescription
                    if(it.profilePicture.isNotEmpty() && it.profilePicture != "demo"){
                        this.profileImage.setImageBitmap(BitmapFactory.decodeFile(it.profilePicture))
                    }
                }

            }
        }

        binding.profileImage.setOnClickListener {
            actionIntent = Intent(Intent.ACTION_PICK)
            actionIntent.type = "image/*"
            if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permission.launch(Manifest.permission.CAMERA)
            } else {
                activityResult.launch(actionIntent)
            }
        }




        home.title?.show()

        binding.backHome.setOnClickListener {
            findNavController().navigateUp()
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