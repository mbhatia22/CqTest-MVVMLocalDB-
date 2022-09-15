package com.ankit.cqlsys.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern


fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    val pattern: Pattern
    val passwordPattern =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=(){}',.*/_])(?=\\S+$).{4,}$"
    pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun EditText.getTrimText() = this.text.trim().toString()

fun Context.toast(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

