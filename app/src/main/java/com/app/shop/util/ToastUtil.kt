package com.app.shop.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.app.shop.R
import io.github.muddz.styleabletoast.StyleableToast


/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：
 *
 */
@SuppressLint("StaticFieldLeak")
object ToastUtil {
        private var context: Context? = null

        fun init(c: Context?) {
            context = c
        }

        fun showNoNetworkToast() {
            context?.let { showToast(it.getString(R.string.network_busy)) }
        }

        /***
         *通用的Toast
         */
        fun showToast(message: String?) {
            context?.let { StyleableToast.makeText(it, message, Toast.LENGTH_SHORT, R.style.my_toast).show() };
        }
}