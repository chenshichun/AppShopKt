package com.app.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.shop.R
import com.app.shop.databinding.ActivitySplashBinding
import com.app.shop.manager.AccountManager
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：启动页
 *
 */
class SplashActivity : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ImmersionBar.with(this)
            .statusBarColor(R.color.white) //状态栏颜色，不写默认透明色
            .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
            .init()

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            startActivity(
                Intent(
                    this@SplashActivity,
                    if (AccountManager.isLogin()) MainActivity::class.java else LoginActivity::class.java
                )
            )
            finish()
        }
    }
}