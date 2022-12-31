package com.app.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.Prod
import com.app.shop.databinding.ItemGoodsBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class GoodsAdapter(private val context: Context, val mData: List<Prod>?) :
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mData == null) {
            return
        }
        holder.nameTv.text = mData[position].prod_name
        Glide.with(context)
            .load(mData[position].pic)
            .placeholder(R.drawable.icon_default_pic)
            .error(R.drawable.icon_default_pic)
            .into(holder.goodsIv)

        holder.tvIntegral.visibility =
            if (mData[position].ori_point.equals("0")) View.GONE else View.VISIBLE
        holder.tvPrice.visibility =
            if (mData[position].ori_point.equals("0")) View.VISIBLE else View.GONE
        holder.tvIntegral.text =
            String.format(context.getString(R.string.goods_integral), mData[position].ori_point)
        holder.tvPrice.text = "￥${mData[position].price}"
        holder.tvCountSell.text = "${mData[position].sold_num}人已购买"
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 10
    }

    inner class ViewHolder(binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val goodsIv: ImageView = binding.ivGoods
        val nameTv: TextView = binding.tvName
        val tvPrice: TextView = binding.tvPrice
        val tvIntegral: TextView = binding.tvIntegral
        val tvCountSell: TextView = binding.tvCountSell
    }

}