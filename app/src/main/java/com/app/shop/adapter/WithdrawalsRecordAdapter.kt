package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemWithdrawalsRecordBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/30
 * 描述：
 */
class WithdrawalsRecordAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<WithdrawalsRecordAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWithdrawalsRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(binding: ItemWithdrawalsRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}