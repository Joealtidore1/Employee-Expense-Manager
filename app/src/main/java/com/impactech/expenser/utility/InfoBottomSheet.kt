package com.impactech.expenser.utility

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.impactech.expenser.R
import com.impactech.expenser.databinding.MsgLayoutBinding

class InfoBottomSheet(private val msg: String, private val title: String? = null): BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MsgLayoutBinding.inflate(inflater, container, false)
        binding.msg.text = msg
        if(title != null)
            binding.infoTitle.text = title
        Handler(Looper.getMainLooper()).postDelayed({dismiss()}, 6000)
        return binding.root
    }

}