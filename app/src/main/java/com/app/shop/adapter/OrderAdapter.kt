package com.app.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.Item
import com.app.shop.bean.Order
import com.app.shop.databinding.ItemOrderBinding
import com.app.shop.service.HomeService

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 */
class OrderAdapter(private val context: Context, val mData: List<Order>?) :
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

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.tvShopName.text = mData!![position].shop_name
        holder.tvConfirm.visibility = View.VISIBLE

        when (mData[position].status) {//1:待付款 2:待发货 3:待收货 4:待评价 5:成功 6:失败
            1 -> {
                holder.tvStatus.text = "待付款"
                holder.tvConfirmLeft.visibility = View.VISIBLE
                holder.tvConfirmLeft.text = "取消订单"
                holder.tvConfirm.text = "去付款"
            }
            2 -> {
                holder.tvConfirmLeft.visibility = View.GONE
                holder.tvStatus.text = "待发货"
                holder.tvConfirm.text = "取消订单"
            }
            3 -> {
                holder.tvConfirmLeft.visibility = View.GONE
                holder.tvConfirm.visibility = View.GONE
                holder.tvStatus.text = "待收货"
                holder.tvConfirm.text = "确认收货"
            }
            4 -> {
                holder.tvConfirmLeft.visibility = View.GONE
                holder.tvStatus.text = "待评价"
                holder.tvConfirm.text = "去评价"
            }
            5 -> {
                holder.tvConfirmLeft.visibility = View.GONE
                holder.tvConfirm.visibility = View.GONE
                holder.tvStatus.text = "交易成功"
            }
            6 -> {
                holder.tvConfirmLeft.visibility = View.GONE
                holder.tvConfirm.visibility = View.GONE
                holder.tvStatus.text = "取消"
            }
        }
        // holder.tvStatus.text = mData[position].status_text
        if (mData[position].verify_code.isEmpty()) {
            holder.tvConfirm.visibility = View.VISIBLE
        } else {
            holder.tvConfirm.visibility = View.GONE
        }
        val orderShopAdapter = OrderShopAdapter(context, mData[position].items)
        holder.recyclerView.layoutManager = LinearLayoutManager(context)
        holder.recyclerView.adapter = orderShopAdapter
        orderShopAdapter.setOnItemClickListener(object : OrderShopAdapter.OnItemClickListener {
            override fun onItemClick(position1: Int) {
                mOnItemClickListener?.onItemClick(position)
            }
        })

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }

        holder.tvConfirmLeft.setOnClickListener {
            mOnItemClickListener!!.onLeftClick(position)
        }

        holder.tvConfirm.setOnClickListener {
            mOnItemClickListener!!.onRightClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLeftClick(position: Int)
        fun onRightClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.mRecyclerView
        val tvShopName: TextView = binding.tvShopName
        val tvStatus: TextView = binding.tvStatus
        val tvConfirm: TextView = binding.tvConfirm
        val tvConfirmLeft: TextView = binding.tvConfirmLeft
    }

}