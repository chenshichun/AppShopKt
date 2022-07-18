package com.app.shop.ui.activity

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.HotSearchBean
import com.app.shop.databinding.ActivityMainBinding
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.MainContract
import com.app.shop.ui.presenter.MainPresenter
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
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        mPresenter?.testPresenter()
        mPresenter?.hotSearch()
        // mPresenter?.hotNewSearch()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            immersionBar {
                statusBarColor(if (destination.id == R.id.navigation_mine) R.color.color_bg else R.color.white)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiRequest.create(HomeService::class.java).getHotSearchNew()
            if (response.body() == null) {
                Logger.d("无网络")
            } else {
                Logger.d(response.body()!!.data?.get(1)?.keyword)
            }
        }
    }

    override fun testView(string: String) {
    }

    override fun hotSearch(data: BaseNetModel<ArrayList<HotSearchBean>>) {
        Logger.d(data.data!![0].keyword)
    }

    override fun hotNewSearch(data: BaseNetModel<ArrayList<HotSearchBean>>) {
        Logger.d(data.data!![0].keyword)
    }

}