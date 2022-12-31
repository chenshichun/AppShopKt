package com.app.shop.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.app.shop.R
import com.app.shop.manager.Constants
import com.app.shop.ui.activity.WebViewActivity
import com.app.shop.util.IntentUtil

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：
 */
class PrivacyDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var view: View = LayoutInflater.from(context).inflate(R.layout.dialog_privacy, null)
        setContentView(view)

        findViewById<TextView>(R.id.tv_yszc).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.WEB_TITLE, "隐私政策")
            bundle.putString(Constants.WEB_URL, Constants.PRIVACT_POLICY_URL)
            IntentUtil.get()!!.goActivity(context, WebViewActivity::class.java, bundle)
        }
        findViewById<TextView>(R.id.tv_yhxy).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.WEB_TITLE, "用户协议")
            bundle.putString(Constants.WEB_URL, Constants.USET_AGREEMENT_URL)
            IntentUtil.get()!!.goActivity(context, WebViewActivity::class.java, bundle)
        }
        findViewById<TextView>(R.id.tv_disagree).setOnClickListener {
            onClickListener!!.disAgree()
        }
        findViewById<TextView>(R.id.tv_agree).setOnClickListener {
            onClickListener!!.agree()
        }

    }

    var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener?): PrivacyDialog? {
        this.onClickListener = onClickListener
        return this
    }

    interface OnClickListener {
        fun agree()
        fun disAgree()
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