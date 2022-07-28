package com.app.shop.loadsir

import com.app.shop.R
import com.kingja.loadsir.callback.Callback

/**
 * @author chenshichun
 * 创建日期：2022/7/28
 * 描述：
 *
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}