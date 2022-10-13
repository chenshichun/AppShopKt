package com.app.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.ShopBean
import com.app.shop.databinding.ItemCartBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：购物车Adapter
 */
class CartAdapter(private val context: Context, val mData: List<ShopBean>?) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.checkBox.isChecked = mData!![position].isCheck
        holder.tvShopName.text = mData[position].shop_name
        val cartGoodsAdapter = CartGoodsAdapter(context, mData[position].prods)
        holder.recyclerView.layoutManager = LinearLayoutManager(context)
        holder.recyclerView.adapter = cartGoodsAdapter
        cartGoodsAdapter.setOnItemClickListener(object :
            CartGoodsAdapter.OnItemClickListener {
            override fun onItemClick(position1: Int) {
                mOnItemClickListener!!.onDetailClick(position, position1)
            }

            override fun deleteClick(position1: Int) {
                mOnItemClickListener!!.deleteClick(position, position1)
            }

            override fun checkClick(position1: Int) {
                mOnItemClickListener!!.checkClick(position, position1)
            }

            override fun reduceClick(position1: Int) {
                mOnItemClickListener!!.reduceClick(position, position1)
            }

            override fun addClick(position1: Int) {
                mOnItemClickListener!!.addClick(position, position1)
            }

            override fun modifyClick(position1: Int) {
                mOnItemClickListener!!.modifyClick(position, position1)
            }
        })

        holder.checkBox.setOnClickListener{
            mOnItemClickListener!!.checkClick(position)
        }
    }

    interface OnItemClickListener {

        fun onDetailClick(position: Int, position1: Int)

        fun checkClick(position: Int)

        fun deleteClick(position: Int, position1: Int)

        fun checkClick(position: Int, position1: Int)

        fun reduceClick(position: Int, position1: Int)

        fun addClick(position: Int, position1: Int)

        fun modifyClick(position: Int, position1: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.mRecyclerView
        val checkBox: CheckBox = binding.checkbox
        val tvShopName: TextView = binding.tvShopName
    }

}