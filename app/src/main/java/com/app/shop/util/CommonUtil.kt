package com.app.shop.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * @author chenshichun
 * 创建日期：2022/8/25
 * 描述：
 *
 */
object CommonUtil {
    private var screenWidthPixels = 0
    private var screenHeightPixels = 0

    fun getScreenWidthPixels(context: Context?): Int {
        if (context == null) {
            return 0
        }
        if (screenWidthPixels > 0) {
            return screenWidthPixels
        }
        val dm = DisplayMetrics()
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getMetrics(dm)
        screenWidthPixels = dm.widthPixels
        return screenWidthPixels
    }

    fun getScreenHeightPixels(context: Context?): Int {
        if (context == null) {
            return 0
        }
        if (screenHeightPixels > 0) {
            return screenHeightPixels
        }
        val dm = DisplayMetrics()
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getMetrics(dm)
        screenHeightPixels = dm.heightPixels
        return screenHeightPixels
    }

    fun dip2px(context: Context?, dipValue: Float): Int {
        if (context == null) {
            return dipValue.toInt()
        }
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}