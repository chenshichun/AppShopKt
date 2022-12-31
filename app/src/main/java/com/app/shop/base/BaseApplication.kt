package com.app.shop.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.LoadingCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.manager.AccountManager
import com.app.shop.util.MmkvUtil
import com.app.shop.util.ToastUtil
import com.kingja.loadsir.core.LoadSir
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import es.dmoral.toasty.Toasty


/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：
 */
class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var mContext: Context? = null
        fun getContext(): Context? {
            return mContext
        }

        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                ClassicsHeader(context)
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext

        // 腾讯mmkv存储初始化
        MMKV.initialize(this)
        if (!(MmkvUtil["APP_START", true] as Boolean)) {
            initialization()
        }
    }

    /*
    * 初始化日志
    * */
    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // 是否显示线程信息，默认为ture
            .methodCount(5) // 显示的方法行数，默认为2
            .tag("cuckoo") // 每个日志的全局标记。默认PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    open fun initialization() {
        // Logger日志初始化
        initLogger()

        // toast初始化
        ToastUtil.init(this)
        AccountManager.init(this)

        // Toasty 初始化
        Toasty.Config.getInstance().supportDarkTheme(true)
            .apply()

        // LoadSir 初始化
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallBack())
            .addCallback(LoadingCallback())
            .addCallback(NetWorkErrorCallBack())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()

        ZXingLibrary.initDisplayOpinion(this)
    }
}