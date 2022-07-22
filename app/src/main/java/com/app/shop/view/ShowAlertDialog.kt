package com.app.shop.view

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.app.shop.R

/**
 * @author chenshichun
 * 创建日期：2022/7/22
 * 描述：
 *
 */
class ShowAlertDialog {
    companion object {
        fun loadingDialog(context: Context?): Dialog? {
            val inflater = LayoutInflater.from(context)
            val v: View = inflater.inflate(R.layout.loading_dialog, null) // 得到加载view
            val loadingDialog = Dialog(context!!, R.style.loading_dialog) // 创建自定义样式dialog
            loadingDialog.setCancelable(true) // 不可以用“返回键”
            loadingDialog.setContentView(
                v, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            ) // 设置布局
            loadingDialog.setCanceledOnTouchOutside(false)
            return loadingDialog
        }
    }
}