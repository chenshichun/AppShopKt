package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemWalletDetailsBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/30
 * 描述：
 */
class WalletDetailAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<WalletDetailAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWalletDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        return 15
    }

    inner class ViewHolder(binding: ItemWalletDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}