package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemRightRecycleBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/6
 * 描述：
 */
class SecondaryClassificationAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<SecondaryClassificationAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickLisenter? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickLisenter?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRightRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = "衣服"
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickLisenter {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return if (mData == null || mData?.size == 0) 6 else mData.size
    }

    inner class ViewHolder(binding: ItemRightRecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.tvName
        val goodsIv: ImageView = binding.ivGoods
    }
}