package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemNewInStoreBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/24
 * 描述：
 */
class NewInStoreAdapter(val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<NewInStoreAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNewInStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imgAdapter = ImgAdapter(null)
        holder.mRecyclerView.layoutManager = GridLayoutManager(context, 3)
        holder.mRecyclerView.adapter = imgAdapter

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

    inner class ViewHolder(binding: ItemNewInStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mRecyclerView: RecyclerView = binding.mRecyclerView
    }
}