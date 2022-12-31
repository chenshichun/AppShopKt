package com.app.shop.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.view.Gravity
import android.view.KeyEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.app.shop.BuildConfig
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UpdateBean
import com.app.shop.bean.event.PageEvent
import com.app.shop.databinding.ActivityMainBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.MainContract
import com.app.shop.ui.presenter.MainPresenter
import com.app.shop.util.AppUtil
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
import com.app.shop.view.dialog.UpdateApkDialog
import com.app.shop.view.tablayout.HomeFactory
import com.app.shop.view.tablayout.TabEntity
import com.blankj.utilcode.util.ToastUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import kotlin.system.exitProcess

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：MainActivity
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.View {

    private var mTitles = arrayListOf("首页", "分类", "本地商家", "购物车", "我的")
    private var mFragments = arrayListOf<Fragment>()
    private val mTabEntities = arrayListOf<CustomTabEntity>()
    private val mIconUnselectedIds = intArrayOf(
        R.drawable.icon_home_normal,
        R.drawable.icon_classification_normal,
        R.drawable.icon_shop_normal,
        R.drawable.icon_cart_normal,
        R.drawable.icon_mine_normal
    )
    private val mIconSelectIds = intArrayOf(
        R.drawable.icon_home_normal,
        R.drawable.icon_classification_normal,
        R.drawable.icon_shop_normal,
        R.drawable.icon_cart_normal,
        R.drawable.icon_mine_normal
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
        // mPresenter!!.androidUpdate()
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
                        IntentUtil.get()!!
                            .goActivity(this@MainActivity, AccountLoginActivity::class.java)
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

    lateinit var updateBean: UpdateBean
    override fun androidUpdate(mData: BaseNetModel<UpdateBean>) {
        updateBean = mData.data!!
        if (mData.data!!.apk_info.version_code.toInt() > AppUtil.getVersionCode(this)!!.toInt()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {// 更新
                showUpdateDialog(mData.data!!);
            }
        }
    }

    private fun showUpdateDialog(updateBean: UpdateBean) {
        val updateApkDialog =
            UpdateApkDialog(this@MainActivity, R.style.CustomDialog)
        updateApkDialog.setOnClickListener(object : UpdateApkDialog.OnClickListener {
            override fun update() {
                downloadApp(updateBean)
                ToastUtil.showToast("后台下载中")
            }
        })
        updateApkDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            showUpdateDialog(updateBean)
        }
    }

    private fun downloadApp(updateBean: UpdateBean) {
        OkHttpUtils.get()
            .url(updateBean.apk_info.download_url)
            .build()
            .execute(object : FileCallBack(
                Environment.getExternalStorageDirectory().absolutePath,
                "yhj.apk"
            ) {
                override fun onError(call: Call?, e: Exception?, id: Int) {
                    ToastUtil.showToast("下载失败")
                }

                override fun onResponse(response: File?, id: Int) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        val contentUri = FileProvider.getUriForFile(
                            this@MainActivity, BuildConfig.APPLICATION_ID + ".provider",
                            response!!
                        )
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
                    } else {
                        intent.setDataAndType(
                            Uri.fromFile(response),
                            "application/vnd.android.package-archive"
                        )
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                }
            })
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

    override fun onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getData(position: PageEvent) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(200)
            binding.mBottomNavigationBar.currentTab = position.position
        }
    }
}