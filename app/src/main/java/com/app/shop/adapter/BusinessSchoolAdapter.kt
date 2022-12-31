package com.app.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.VideoBean
import com.app.shop.databinding.ItemBusinessSchoolBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/9/27
 * 描述：
 */
class BusinessSchoolAdapter(private val context: Context, val mData: List<VideoBean>?) :
    RecyclerView.Adapter<BusinessSchoolAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBusinessSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(mData!![position].cover).error(R.drawable.icon_default_pic)
            .placeholder(R.drawable.icon_default_pic).into(holder.ivVideo)
        holder.itemView.setOnClickListener{
            mOnItemClickListener!!.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemBusinessSchoolBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivVideo: ImageView = binding.ivVideo
    }

}