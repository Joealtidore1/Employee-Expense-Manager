package com.impactech.expenser

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.impactech.expenser.databinding.ActivityImagePreviewBinding
import java.io.ByteArrayOutputStream

class ImagePreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagePreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        val uri = intent.getStringExtra("uri")
        if (uri != null) {
            byteArrayToBitmap(uri)
        }

        binding.crop.setOnClickListener {
            val photo = binding.cropImageView.croppedImage
            if(photo != null) {
                val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val imageInByte = stream.toByteArray()
                val imageUri = Base64.encodeToString(imageInByte, Base64.DEFAULT)

                val res = Intent().putExtra("image", imageUri)
                this.setResult(RESULT_OK, res)
                finish()
            }else{
                this.setResult(RESULT_CANCELED)
                finish()
            }
        }
    }

    private fun byteArrayToBitmap(imageUri: String){
        val bytes = Base64.decode(imageUri, Base64.DEFAULT)
        val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        binding.cropImageView.setImageBitmap(bitmap)
    }
}