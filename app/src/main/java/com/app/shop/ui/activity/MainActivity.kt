package com.app.shop.ui.activity

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.HotSearchBean
import com.app.shop.databinding.ActivityMainBinding
import com.app.shop.manager.AccountManager
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.MainContract
import com.app.shop.ui.presenter.MainPresenter
import com.app.shop.util.ToastUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.View {

    override fun getPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun initView() {
        Logger.d(AccountManager.isLogin())
        Logger.d(AccountManager.getToken())
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            immersionBar {
                statusBarColor(if (destination.id == R.id.navigation_mine) R.color.color_bg else R.color.white)
            }
        }

    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}