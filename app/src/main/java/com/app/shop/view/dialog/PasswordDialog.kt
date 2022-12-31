package com.app.shop.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.app.shop.R

/**
 * @author chenshichun
 * 创建日期：2022/10/21
 * 描述：
 */
class PasswordDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    lateinit var etPassword: EditText
    lateinit var tvCancel: TextView
    lateinit var tvPay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_password, null)
        setContentView(view)

        etPassword = findViewById(R.id.et_password)
        tvCancel = findViewById(R.id.tv_cancel)
        tvPay = findViewById(R.id.tv_pay)
        tvCancel.setOnClickListener {
            onClickListener!!.cancel()
        }
        tvPay.setOnClickListener {
            onClickListener!!.pay(etPassword.text.toString())
        }
    }

    var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener?): PasswordDialog? {
        this.onClickListener = onClickListener
        return this
    }

    interface OnClickListener {
        fun cancel()
        fun pay(psw: String)
    }

    override fun show() {
        super.show()
        val layoutParams = window!!.attributes
        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        // setPadding 设置布局与屏幕四个方向的间距
        window!!.decorView.setPadding(88, 0, 88, 0)
        window!!.attributes = layoutParams
    }

}