package com.app.shop.loadsir

import android.content.Context
import android.view.View
import com.app.shop.R
import com.kingja.loadsir.callback.Callback

/**
 * @author chenshichun
 * 创建日期：2022/7/28
 * 描述：
 *
 */
class EmptyCallBack : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

    //当前Callback的点击事件，如果返回true则覆盖注册时的onReload()，如果返回false则两者都执行，先执行onReloadEvent()。
    override fun onReloadEvent(
        context: Context,
        view: View
    ): Boolean {
        return false
    }
}