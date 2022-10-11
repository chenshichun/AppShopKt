package com.app.shop.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build


/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
object AppUtil {
    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    fun isAliPayInstalled(context: Context): Boolean {
        val uri = Uri.parse("alipays://platformapi/startApp")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val componentName = intent.resolveActivity(context.packageManager)
        return componentName != null
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    fun isWeixinAvilible(context: Context): Boolean {
        val packageManager = context.packageManager // 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }

    fun getVersion(context: Context): String? {
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            "无法获取到版本号"
        }
    }

    fun getVersionCode(context: Context): String? //获取版本号(内部识别号)  
    {
        return try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            pi.versionCode.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block  
            e.printStackTrace()
            "0"
        }
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    fun getSystemModel(): String? {
        return Build.MODEL
    }

    fun call(context: Context?, phone: String) {
        AlertDialog.Builder(context)
            .setTitle("是否拨打电话")
            .setMessage("电话号码$phone")
            .setPositiveButton(
                "确定"
            ) { dialog, which -> context?.let { intent2Call(it, phone) } }.setNegativeButton(
                "取消"
            ) { dialogInterface, i -> dialogInterface.dismiss() }
            .show()
    }

    /**
     * 跳转到拨打电话界面
     *
     * @param context
     * @param phoneNumber 传入的电话号码
     */
    fun intent2Call(context: Context, phoneNumber: String) {
        val callIntent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:$phoneNumber")
        )
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(callIntent)
    }
}