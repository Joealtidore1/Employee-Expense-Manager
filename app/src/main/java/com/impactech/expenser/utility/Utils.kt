package com.impactech.expenser.utility

import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.hide() {this.visibility = View.GONE}

fun View.show() {this.visibility = View.VISIBLE}

fun TextView.extractText() = this.text.toString()

fun EditText.extractText() = this.text.toString()

fun EditText.validate(): Boolean{
    return this.text.isNotBlank()
}

fun TextView.validate(): Boolean{
    return this.text.isNotBlank()
}

fun Activity.bottomSheet(msg: String){
    InfoBottomSheet(msg)
        .show((this as AppCompatActivity).supportFragmentManager, "InfoBottomSheet")
}

fun Fragment.toast(msg: String){
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Activity.bottomSheet(msg: String, title: String){
    InfoBottomSheet(msg, title)
        .show((this as AppCompatActivity).supportFragmentManager, "InfoBottomSheet")
}

fun Fragment.bottomSheet(msg: String){
    InfoBottomSheet(msg)
        .show(childFragmentManager, "InfoBottomSheet")
}

fun Fragment.bottomSheet(msg: String, title: String){
    InfoBottomSheet(msg, title)
        .show(childFragmentManager, "InfoBottomSheet")
}

fun Activity.toast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.savePreference(key: String, value: Any) {
    val sharedPreferences = getSharedPreferences(this.packageName, Activity.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    if(value is String)
        editor.putString(key, value)
    if(value is Boolean)
        editor.putBoolean(key, value)
    if(value is Int)
        editor.putInt(key, value)
    editor.apply()
}

fun Activity.getPreferenceS(key: String): String? {
    val sharedPreferences = getSharedPreferences(this.packageName, Activity.MODE_PRIVATE)
    return sharedPreferences.getString(key, "")
}

fun Activity.getPreferenceB(key: String): Boolean {
    val sharedPreferences = getSharedPreferences(this.packageName, Activity.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, false)
}

fun Activity.getPreferenceI(key: String): Int {
    val sharedPreferences = getSharedPreferences(this.packageName, Activity.MODE_PRIVATE)
    return sharedPreferences.getInt(key, 0)
}