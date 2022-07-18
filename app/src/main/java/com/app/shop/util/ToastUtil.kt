package com.app.shop.util

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty


/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：
 *
 */
class ToastUtil {
    companion object {
        private var context: Context? = null

        fun init(c: Context?) {
            context = c
        }

        /***
         *通常的Toast
         */
        fun showToast(message: String) {
            context?.let { Toasty.normal(it, "", Toast.LENGTH_SHORT).show() }
        }

        /***
         * 带图片Toast
         */
        fun showToast(message: String, int: Int) {
            context?.let { Toasty.normal(it, "", int).show() }
        }
    }
}