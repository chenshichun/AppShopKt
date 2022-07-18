package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemOrderGoodsBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 */
class OrderShopAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<OrderShopAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = "衣服"
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class ViewHolder(binding: ItemOrderGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.tvGoodsName
    }

}