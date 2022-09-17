package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.Product
import com.app.shop.databinding.ItemImgBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/8/24
 * 描述：
 */
class ImgAdapter(val context: Context, val mData: List<Product>?) :
    RecyclerView.Adapter<ImgAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(mData!![position].pic).into(holder.ivGoods)

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

    inner class ViewHolder(binding: ItemImgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivGoods: ImageView = binding.ivGoods
    }

}