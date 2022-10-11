package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.CartOrderDetailBean
import com.app.shop.databinding.ItemCartOrderBinding

/**
 * @author chenshichun
 * 创建日期：2022/10/11
 * 描述：
 */
class OrderCartAdapter(private val context: Context, val mData: List<CartOrderDetailBean>?) :
    RecyclerView.Adapter<OrderCartAdapter.ViewHolder>() {
    private lateinit var orderCartGoodsAdapter: OrderCartGoodsAdapter

    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvShopName.text = mData!![position].order_name
        orderCartGoodsAdapter = OrderCartGoodsAdapter(context, mData[position].items)
        holder.mRecyclerView.layoutManager = LinearLayoutManager(context)
        holder.mRecyclerView.adapter = orderCartGoodsAdapter
        holder.btConfirm.setOnClickListener {
            mOnItemClickListener!!.onPayClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onPayClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemCartOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvShopName: TextView = binding.tvShopName
        val mRecyclerView: RecyclerView = binding.mRecyclerView
        val btConfirm: Button = binding.btConfirm
    }
}