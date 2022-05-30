package com.impactech.expenser

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import com.impactech.expenser.databinding.ActivityAuthenticationBinding
import com.impactech.expenser.domain.model.Constant.userId
import com.impactech.expenser.presentation.states_and_events.AuthEvent
import com.impactech.expenser.presentation.view_models.AuthViewModel
import com.impactech.expenser.utility.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Authentication : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.login.setOnClickListener {
            if (!binding.username.validate()) {
                bottomSheet(getString(R.string.validation_error_message, "Username"))
                return@setOnClickListener
            }

            if (!binding.password.validate()) {
                bottomSheet(getString(R.string.validation_error_message, "Password"))
                return@setOnClickListener
            }

            /*if ("demo" == binding.username.text.toString()) {
                if ("demo" == binding.password.text.toString()) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                    savePreference("loggedIn", true)
                    savePreference("userId", userId)
                } else {
                    bottomSheet(getString(R.string.login_error_message), "Authentication Error")
                }
            }*/


            viewModel.onEvent(
                AuthEvent.Login(
                    binding.username.extractText(),
                    binding.password.extractText()
                )
            )
            viewModel.isSuccess.observe(this) {
                if (viewModel.isSuccess.value!!) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                }
            }


        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.isSuccess.observe(this) {
            if (it) {
                savePreference("loggedIn", true)
                savePreference("userId", userId)
            } else {
                bottomSheet(getString(R.string.login_error_message), "Authentication Error")
            }
        }

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