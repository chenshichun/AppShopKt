package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.ShopBean
import com.app.shop.databinding.ItemCartConfirmBinding

/**
 * @author chenshichun
 * 创建日期：2022/10/10
 * 描述：
 */
class ConfirmCartAdapter(private val context: Context, val mData: List<ShopBean>?) :
    RecyclerView.Adapter<ConfirmCartAdapter.ViewHolder>() {
    private lateinit var confirmCartOrderAdapter: ConfirmCartOrderAdapter

    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartConfirmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvShopName.text = mData!![position].shop_name
        confirmCartOrderAdapter = ConfirmCartOrderAdapter(context,mData[position].prods)
        holder.mRecyclerView.layoutManager = LinearLayoutManager(context)
        holder.mRecyclerView.adapter = confirmCartOrderAdapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemCartConfirmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvShopName: TextView = binding.tvShopName
        val mRecyclerView: RecyclerView = binding.mRecyclerView
    }
}