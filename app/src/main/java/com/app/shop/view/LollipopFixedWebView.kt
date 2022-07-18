package com.app.shop.view

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：解决在Android5.x版本中会报以下的错误
 *
 */
class LollipopFixedWebView : WebView {
    constructor(context: Context) : super(getFixedContext(context)) {}
    constructor(context: Context, attrs: AttributeSet?) : super(
        getFixedContext(context), attrs
    ) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        getFixedContext(context), attrs, defStyleAttr
    ) {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        getFixedContext(context), attrs, defStyleAttr, defStyleRes
    ) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        privateBrowsing: Boolean
    ) : super(
        getFixedContext(context), attrs, defStyleAttr, privateBrowsing
    ) {
    }

    companion object {
        fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 23) context.createConfigurationContext(
                Configuration()
            ) else context
        }
    }
}