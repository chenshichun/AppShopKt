package com.app.shop.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.app.shop.R
import com.app.shop.base.BaseApplication
import com.app.shop.databinding.ActivitySplashBinding
import com.app.shop.manager.AccountManager
import com.app.shop.util.IntentUtil
import com.app.shop.util.MmkvUtil
import com.app.shop.view.dialog.PrivacyDialog
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：启动页
 */
class SplashActivity : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ImmersionBar.with(this)
            .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
            .init()

        if (MmkvUtil["APP_START", true] as Boolean) {
            val privacyDialog = PrivacyDialog(this, R.style.CustomDialog)
            privacyDialog.setOnClickListener(object : PrivacyDialog.OnClickListener {
                override fun agree() {
                    privacyDialog.dismiss()
                    MmkvUtil.put("APP_START", false)
                    val app = application as BaseApplication
                    app.initialization()
                    jump()
                }

                override fun disAgree() {
                    privacyDialog.dismiss()
                    finish()
                }

            })
            privacyDialog.show()
        } else {
            jump()
        }



        binding!!.tvJump.setOnClickListener {
            flag = 1
            if (MmkvUtil["welcomeGuide", true] as Boolean) {
                IntentUtil.get()!!.goActivity(this@SplashActivity, WelcomeGuideActivity::class.java)
                MmkvUtil.put("welcomeGuide", false)
            } else {
                IntentUtil.get()!!.goActivity(
                    this@SplashActivity,
                    if (AccountManager.isLogin()) MainActivity::class.java else AccountLoginActivity::class.java
                )
            }
            finish()
        }
    }

    private fun jump() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            if (MmkvUtil["welcomeGuide", true] as Boolean) {
                if (flag == 0) {
                    IntentUtil.get()!!
                        .goActivity(this@SplashActivity, WelcomeGuideActivity::class.java)
                    MmkvUtil.put("welcomeGuide", false)
                }
            } else {
                if (flag == 0) {
                    IntentUtil.get()!!.goActivity(
                        this@SplashActivity,
                        if (AccountManager.isLogin()) MainActivity::class.java else AccountLoginActivity::class.java
                    )
                }
            }
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}