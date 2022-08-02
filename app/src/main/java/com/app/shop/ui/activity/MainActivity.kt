package com.app.shop.ui.activity

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityMainBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.MainContract
import com.app.shop.ui.presenter.MainPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
import com.app.shop.view.tablayout.HomeFactory
import com.app.shop.view.tablayout.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import kotlin.math.log
import kotlin.system.exitProcess


class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.View {

    private var mTitles = arrayListOf("首页", "分类", "线下联盟商家", "购物车", "我的")
    private var mFragments = arrayListOf<Fragment>()
    private val mTabEntities = arrayListOf<CustomTabEntity>()
    private val mIconUnselectedIds = intArrayOf(
        R.drawable.icon_home,
        R.drawable.icon_classification,
        R.drawable.icon_shop,
        R.drawable.icon_cart,
        R.drawable.icon_mine
    )
    private val mIconSelectIds = intArrayOf(
        R.drawable.icon_home_select,
        R.drawable.icon_classification_select,
        R.drawable.icon_shop_selsect,
        R.drawable.icon_cart_select,
        R.drawable.icon_mine_select
    )
    private var currentPosition = 0


    override fun getPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun initView() {
        immersionBar {
            transparentStatusBar()
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
        }

        initFragments()
        initTabItems()

    }

    private fun initFragments() {
        mFragments = ArrayList()
        mFragments.clear()
        HomeFactory.getFragment(0)?.let { mFragments.add(it) }
        HomeFactory.getFragment(1)?.let { mFragments.add(it) }
        HomeFactory.getFragment(2)?.let { mFragments.add(it) }
        HomeFactory.getFragment(3)?.let { mFragments.add(it) }
        HomeFactory.getFragment(4)?.let { mFragments.add(it) }
    }

    private fun initTabItems() {
        for (i in mTitles.indices) {
            Logger.d(mTitles[i])
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectedIds[i]))
        }
        binding.mBottomNavigationBar.setTabData(
            mTabEntities,
            this,
            R.id.m_fl_content_container,
            mFragments
        )
        binding.mBottomNavigationBar.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                when (position) {
                    0 -> currentPosition = 0
                    1 -> currentPosition = 1
                    2 -> currentPosition = 2
                    3 -> currentPosition = 3
                    4 -> if (!AccountManager.isLogin()) {
                        IntentUtil.get()!!.goActivity(this@MainActivity, LoginActivity::class.java)
                        binding.mBottomNavigationBar.currentTab = currentPosition
                    } else {
                        currentPosition = 4
                    }
                }
            }

            override fun onTabReselect(position: Int) {
                Logger.d(position)
            }
        })
        binding.mBottomNavigationBar.currentTab = currentPosition
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    private var isExit = false

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
        if (!isExit) {
            isExit = true
            ToastUtil.showToast("再按一次退出程序")
            mHandler.sendEmptyMessageDelayed(0, 2000)
        } else {
            finish()
            exitProcess(0)
        }
    }


    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            isExit = false
        }
    }
}