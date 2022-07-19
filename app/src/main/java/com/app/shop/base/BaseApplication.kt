package com.app.shop.base

import android.app.Application
import android.content.Context
import com.app.shop.manager.AccountManager
import com.app.shop.util.ToastUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV


/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：
 *
 */
class BaseApplication : Application() {
    companion object {
        var mContext: Context? = null
        fun getContext(): Context? {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        // Logger日志初始化
        initLogger()
        // 腾讯mmkv存储初始化
        MMKV.initialize(this);
        // toast初始化
        ToastUtil.init(this)

        AccountManager.init(this)
    }

    /*
    * 初始化日志
    * */
    fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // 是否显示线程信息，默认为ture
            .methodCount(5) // 显示的方法行数，默认为2
            .tag("cuckoo") // 每个日志的全局标记。默认PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}