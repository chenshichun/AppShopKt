package com.app.shop.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author chenshichun
 * 创建日期：2022/7/22
 * 描述：
 */
object FileUtil{
    fun getAssetsFileText(context: Context, fileName:String):String{
        val strBuilder=StringBuilder()
        val assetManager=context.assets
        val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
        bf.use { strBuilder.append(it.readLine())}
        bf.close()
        return strBuilder.toString()
    }
}