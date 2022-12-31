package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemRefundGoodsBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：
 *
 */
class RefundGoodsAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<RefundGoodsAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRefundGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    inner class ViewHolder(binding: ItemRefundGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}