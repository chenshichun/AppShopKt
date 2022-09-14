package com.app.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.SkuInfo
import com.app.shop.databinding.ItemSpecBinding
import com.google.android.flexbox.*

/**
 * @author chenshichun
 * 创建日期：2022/9/13
 * 描述：
 */
class SpecAdapter(private val context: Context, val mData: List<SkuInfo>?) :
    RecyclerView.Adapter<SpecAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSpecBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.tvSkuName.text = mData!![position].sku

        val skuPropAdapter = SkuPropAdapter(context, mData[position].sku_prop)
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.justifyContent = JustifyContent.FLEX_START
        holder.mRecyclerView.layoutManager = layoutManager
        holder.mRecyclerView.adapter = skuPropAdapter
        skuPropAdapter.setOnItemClickListener(object : SkuPropAdapter.OnItemClickListener {
            override fun onItemClick(position1: Int) {
                mOnItemClickListener!!.onItemClick(position, position1)
            }
        })
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, position1: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemSpecBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvSkuName: TextView = binding.tvSkuName
        val mRecyclerView: RecyclerView = binding.mRecyclerView
    }
}