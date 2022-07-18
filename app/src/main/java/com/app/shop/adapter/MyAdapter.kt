package com.app.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.shop.R


/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class MyAdapter(context: Context?, images: Array<Int>, text: Array<String>) :
    BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private val images: Array<Int>
    private val text: Array<String>

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View = layoutInflater.inflate(R.layout.item_grid, null)
        val iv: ImageView = v.findViewById<View>(R.id.iv_gridView_item) as ImageView
        val tv = v.findViewById<View>(R.id.tv_gridView_item) as TextView
        iv.setImageResource(images[position])
        tv.text = text[position]
        return v
    }

    init {
        this.images = images
        this.text = text
        layoutInflater = LayoutInflater.from(context)
    }
}