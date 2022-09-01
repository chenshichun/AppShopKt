package com.app.shop.util

import android.content.Context
import com.app.shop.util.AMapUtil
import android.content.Intent
import android.text.TextUtils
import com.app.shop.util.ToastUtil
import android.content.pm.PackageInfo
import android.net.Uri
import com.app.shop.R
import java.lang.Exception

/**
 * @author chenshichun
 * 创建日期：2021/6/10
 * 描述：
 */
object AMapUtil {
    /**
     * 路线规划
     *
     * @param slat 起点纬度
     * @param slon 起点经度
     * @param dlat 终点纬度
     * @param dlon 终点经度
     */
    fun route(context: Context, slat: String, slon: String, dlat: String, dlon: String) {
        if (isInstallApp(context, "com.autonavi.minimap")) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.setPackage("com.autonavi.minimap")
            var uri =
                "androidamap://route?" + "sourceApplication=" + context.getString(R.string.app_name)
            //如果设置了起点
            if (!TextUtils.isEmpty(slat) && !TextUtils.isEmpty(slon)) {
                uri += "&slat=$slat&slon=$slon"
            }
            uri += "&dlat=" + dlat +
                    "&dlon=" + dlon +
                    "&dev=" + 0 +
                    "&t=" + 0 +
                    "&t=" + 0
            intent.data = Uri.parse(uri)
            context.startActivity(intent)
        } else {
            var uri = "https://uri.amap.com/navigation?"
            //如果设置了起点
            if (!TextUtils.isEmpty(slat) && !TextUtils.isEmpty(slon)) {
                uri += "from=$slon,$slat,起点"
            }
            uri += "&to=" + dlon + "," + dlat + ",终点" +
                    "&mode=car"
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(uri)
            )
            context.startActivity(intent)
        }
    }

    /**
     * 打开高德地图（公交出行，起点位置使用地图当前位置）
     *
     *
     * t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     */
    fun openGaoDeMap(context: Context, dlat: String, dlon: String, dname: String) {
        if (isInstallApp(context, "com.autonavi.minimap")) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setPackage("com.autonavi.minimap")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse(
                "androidamap://route?sourceApplication=" + R.string.app_name
                        + "&sname=我的位置&dlat=" + dlat
                        + "&dlon=" + dlon
                        + "&dname=" + dname
                        + "&dev=0&m=0&t=0"
            )
            context.startActivity(intent)
        } else {
            ToastUtil.showToast("高德地图未安装")
        }
    }

    /*
     * 打开百度地图（公交出行，起点位置使用地图当前位置）
     *
     * mode = transit（公交）、driving（驾车）、walking（步行）和riding（骑行）. 默认:driving
     * 当 mode=transit 时 ： sy = 0：推荐路线 、 2：少换乘 、 3：少步行 、 4：不坐地铁 、 5：时间短 、 6：地铁优先
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     */
    fun openBaiduMap(context: Context, dlat: String?, dlon: String?, dname: String) {
        if (isInstallApp(context, "com.baidu.BaiduMap")) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(
                "baidumap://map/direction?origin=我的位置&destination=name:"
                        + dname
                        + "|latlng:" + gd2bd(
                    java.lang.Double.valueOf(dlat),
                    java.lang.Double.valueOf(dlon)
                )[0] + "," + gd2bd(
                    java.lang.Double.valueOf(dlat),
                    java.lang.Double.valueOf(dlon)
                )[1]
                        + "&mode=driving&sy=3&index=0&target=1"
            )
            context.startActivity(intent)
        } else {
            ToastUtil.showToast("百度地图未安装")
        }
    }

    /**
     * 打开腾讯地图（公交出行，起点位置使用地图当前位置）
     *
     *
     * 公交：type=bus，policy有以下取值
     * 0：较快捷 、 1：少换乘 、 2：少步行 、 3：不坐地铁
     * 驾车：type=drive，policy有以下取值
     * 0：较快捷 、 1：无高速 、 2：距离短
     * policy的取值缺省为0
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     */
    fun openTencent(context: Context, dlat: String, dlon: String, dname: String) {
        if (isInstallApp(context, "com.tencent.map")) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(
                "qqmap://map/routeplan?type=bus&from=我的位置&fromcoord=0,0"
                        + "&to=" + dname
                        + "&tocoord=" + dlat + "," + dlon
                        + "&policy=1&referer=myapp"
            )
            context.startActivity(intent)
        } else {
            ToastUtil.showToast("腾讯地图未安装")
        }
    }

    /**
     * 检测应用是否安装
     */
    fun isInstallApp(context: Context, packageName: String?): Boolean {
        var packageInfo: PackageInfo?
        try {
            packageInfo = context.packageManager.getPackageInfo(packageName!!, 0)
        } catch (e: Exception) {
            packageInfo = null
            e.printStackTrace()
        }
        return packageInfo != null
    }

    private fun gd2bd(lat: Double, lng: Double): DoubleArray {
        val X_PI = Math.PI * 3000.0 / 180.0
        val z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI)
        val theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI)
        val bd_lng = z * Math.cos(theta) + 0.0065
        val bd_lat = z * Math.sin(theta) + 0.006
        return doubleArrayOf(bd_lat, bd_lng)
    }
}