package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemOfflineShopBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/11
 * 描述：
 *
 */
class OfflineShopAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<OfflineShopAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOfflineShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNavigation.setOnClickListener {
            mOnItemClickListener!!.onNavigationClick(position)
        }
        holder.itemView.setOnClickListener {
            mOnItemClickListener!!.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onNavigationClick(position: Int)
    }

    override fun getItemCount(): Int {
        return 15
    }

    inner class ViewHolder(binding: ItemOfflineShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNavigation: TextView = binding.tvNavigation
    }

}