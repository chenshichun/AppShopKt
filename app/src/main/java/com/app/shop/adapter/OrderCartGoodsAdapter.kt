package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.OrderFilterItemBean
import com.app.shop.databinding.ItemConfirmOrderBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/10/11
 * 描述：
 */
class OrderCartGoodsAdapter(private val context: Context, val mData: List<OrderFilterItemBean>?) :
    RecyclerView.Adapter<OrderCartGoodsAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemConfirmOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(mData!![position].pic).error(R.drawable.icon_default_pic)
            .placeholder(R.drawable.icon_default_pic).into(holder.goodsIv)
        holder.tvGoodsTitle.text = mData[position].prod_name
        holder.tvAttr.text = mData[position].sku_name
        holder.tvNum.text =
            String.format(context.getString(R.string.num), mData[position].prod_count.toString())
        holder.tvPrice.text =
            String.format(
                context.getString(if (mData[position].point!!.toInt() != 0) R.string.goods_integral else R.string.price),
                if (mData[position].point!!.toInt() != 0) mData[position].point else mData[position].price
            )
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemConfirmOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val goodsIv: ImageView = binding.ivGoods
        val tvGoodsTitle: TextView = binding.tvGoodsTitle
        val tvPrice: TextView = binding.tvPrice
        val tvAttr: TextView = binding.tvAttr
        val tvNum: TextView = binding.tvNum
        val llOrder: LinearLayout = binding.llOrder
    }
}