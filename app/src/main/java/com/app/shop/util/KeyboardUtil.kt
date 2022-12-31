package com.app.shop.util

import android.view.inputmethod.InputMethodManager
import android.app.Activity
import android.content.Context

object KeyboardUtil {
    fun hideKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && (context as Activity).currentFocus != null) {
            if (context.currentFocus!!.windowToken != null) {
                imm.hideSoftInputFromWindow(
                    context.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }
}