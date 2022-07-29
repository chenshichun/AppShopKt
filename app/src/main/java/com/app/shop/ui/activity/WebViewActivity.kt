package com.app.shop.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.app.shop.R
import com.app.shop.databinding.ActivityWebViewBinding
import com.app.shop.manager.Constants
import com.gyf.immersionbar.ImmersionBar
import com.just.agentweb.AgentWeb


class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var mAgentWeb: AgentWeb
    private var url: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImmersionBar.with(this)
            .statusBarColor(R.color.white) //状态栏颜色，不写默认透明色
            .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
            .init()

        title = intent.getStringExtra(Constants.WEB_TITLE)
        url = intent.getStringExtra(Constants.WEB_URL)

        binding.viewHead.tvTitle.text = title
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.llRoot, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }

}