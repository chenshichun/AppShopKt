package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.ProdCartBean
import com.app.shop.databinding.ItemCartShopGoodsBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：购物车2级adapter
 */
class CartGoodsAdapter(private val context: Context, val mData: List<ProdCartBean>?) :
    RecyclerView.Adapter<CartGoodsAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartShopGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBox.isChecked = mData!![position].isCheck

        holder.itemView.setOnClickListener {
            mOnItemClickListener!!.onItemClick(position)
        }

        holder.checkBox.setOnClickListener {
            mOnItemClickListener!!.checkClick(position)
        }

        holder.btDelete.setOnClickListener {
            mOnItemClickListener!!.deleteClick(position)
        }

        holder.ivReduce.setOnClickListener {
            mOnItemClickListener!!.reduceClick(position)
        }

        holder.ivAdd.setOnClickListener {
            mOnItemClickListener!!.addClick(position)
        }

        holder.tvNum.setOnClickListener {
            mOnItemClickListener!!.modifyClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position1: Int)

        fun deleteClick(position1: Int)

        fun checkClick(position1: Int)

        fun reduceClick(position1: Int)

        fun addClick(position1: Int)

        fun modifyClick(position1: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemCartShopGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val checkBox: CheckBox = binding.checkbox
        val ivGoods: ImageView = binding.ivGoods
        val tvName: TextView = binding.tvName
        val ivReduce: ImageView = binding.ivReduce
        val ivAdd: ImageView = binding.ivAdd
        val tvNum: TextView = binding.tvNum
        val btDelete: Button = binding.btDelete
    }
}