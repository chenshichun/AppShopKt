package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemGoodsBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class GoodsAdapter(private val context: Context, val mData: List<String>?) :
    RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        return 15
    }

    inner class ViewHolder(binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val goodsIv: ImageView = binding.ivGoods
        val nameTv: TextView = binding.tvName
        val tvIntegral: TextView = binding.tvIntegral
        val tvCountSell: TextView = binding.tvCountSell
    }

}