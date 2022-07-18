package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemCartBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：购物车Adapter
 *
 */
class CartAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickLisenter? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickLisenter?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartGoodsAdapter = CartGoodsAdapter(context, null)
        holder.recyclerView.layoutManager = LinearLayoutManager(context)
        holder.recyclerView.adapter = cartGoodsAdapter
        cartGoodsAdapter.setOnItemClickListener(object : CartGoodsAdapter.OnItemClickLisenter {
            override fun onItemClick(position: Int) {
            }
        })

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickLisenter {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class ViewHolder(binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.mRecyclerView
    }

}