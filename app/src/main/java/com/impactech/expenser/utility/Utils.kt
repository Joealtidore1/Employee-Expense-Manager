package com.impactech.expenser.utility

import android.view.View
import android.widget.EditText

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.hide() {this.visibility = View.GONE}

fun View.show() {this.visibility = View.VISIBLE}

fun EditText.extractText() = this.text.toString()