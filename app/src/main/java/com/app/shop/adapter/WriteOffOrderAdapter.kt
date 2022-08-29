package com.app.shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemWriteOffOrderBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/24
 * 描述：
 */
class WriteOffOrderAdapter(val mData: List<String>?) :
    RecyclerView.Adapter<WriteOffOrderAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWriteOffOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        return 6
    }

    inner class ViewHolder(binding: ItemWriteOffOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}