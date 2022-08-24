package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.ChildrenBean
import com.app.shop.databinding.ItemRightRecycleBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/7/6
 * 描述：
 */
class SecondaryClassificationAdapter(private val context: Context, val mData: List<ChildrenBean>?) :
    RecyclerView.Adapter<SecondaryClassificationAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRightRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = mData!![position].category_name
        Glide.with(context).load(mData[position].pic).placeholder(R.drawable.icon_default_pic)
            .into(holder.goodsIv)
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

    inner class ViewHolder(binding: ItemRightRecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.tvName
        val goodsIv: ImageView = binding.ivGoods
    }
}