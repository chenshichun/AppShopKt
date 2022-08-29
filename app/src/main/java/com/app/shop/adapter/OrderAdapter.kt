package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemOrderBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 */
class OrderAdapter (private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderShopAdapter = OrderShopAdapter(context, null)
        holder.recyclerView.layoutManager = LinearLayoutManager(context)
        holder.recyclerView.adapter = orderShopAdapter
        orderShopAdapter.setOnItemClickListener(object : OrderShopAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

            }
        })

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ViewHolder(binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.mRecyclerView
    }

}