package com.app.shop.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.app.shop.R

/**
 * @author chenshichun
 * 创建日期：2022/11/28
 * 描述：
 */
class UpdateApkDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    lateinit var updateBtn: Button
    lateinit var tv_update_content: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_update_dialog, null)
        setContentView(view)

        updateBtn = view.findViewById(R.id.btn_update)
        tv_update_content= view.findViewById(R.id.tv_update_content)

        updateBtn.setOnClickListener {
            onClickListener!!.update()
        }
    }

    var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener?): UpdateApkDialog? {
        this.onClickListener = onClickListener
        return this
    }

    interface OnClickListener {
        fun update()
    }
}