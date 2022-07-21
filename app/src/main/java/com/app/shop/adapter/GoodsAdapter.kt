package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.Prod
import com.app.shop.databinding.ItemGoodsBinding
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class GoodsAdapter(private val context: Context, val mData: ArrayList<Prod>?) :
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
        holder.nameTv.text = mData!![position].prod_name
        Glide.with(context)
            .load("https://img-blog.csdnimg.cn/20201014180756922.png?x-oss-process=image/resize,m_fixed,h_64,w_64"/*mData[position].pic*/)
            .into(holder.goodsIv)
        holder.tvIntegral.text = "￥${mData[position].price}"
        holder.tvCountSell.text = "${mData[position].sold_num}人已购买"
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

    inner class ViewHolder(binding: ItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val goodsIv: ImageView = binding.ivGoods
        val nameTv: TextView = binding.tvName
        val tvIntegral: TextView = binding.tvIntegral
        val tvCountSell: TextView = binding.tvCountSell
    }

}