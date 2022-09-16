package com.app.shop.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.Item
import com.app.shop.databinding.ItemOrderGoodsBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 */
class OrderShopAdapter(private val context: Context, val mData: List<Item>?) :
    RecyclerView.Adapter<OrderShopAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(mData!![position].pic).error(R.drawable.icon_default_pic)
            .placeholder(R.drawable.icon_default_pic).into(holder.ivGoods)
        holder.nameTv.text = mData[position].prod_name
        holder.tvSpecification.text = mData[position].sku_name
        holder.tvPrice.text = String.format(
            context.getString(if (mData[position].point.toInt() != 0) R.string.goods_integral else R.string.price),
            if (mData[position].point.toInt() != 0) mData[position].point else mData[position].price
        )

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemOrderGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.tvGoodsName
        val tvSpecification: TextView = binding.tvSpecification
        val tvPrice: TextView = binding.tvPrice
        val ivGoods: ImageView = binding.ivGoods
    }

}