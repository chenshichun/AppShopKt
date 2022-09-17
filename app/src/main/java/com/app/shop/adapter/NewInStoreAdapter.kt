package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.Store
import com.app.shop.databinding.ItemNewInStoreBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/8/24
 * 描述：
 */
class NewInStoreAdapter(val context: Context, val mData: List<Store>?) :
    RecyclerView.Adapter<NewInStoreAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNewInStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(mData!![position].store_pic).into(holder.ivStore)
        holder.tvName.text = mData[position].store_name
        holder.tvGoodsSell.text = String.format(context.getString(R.string.goods_sell), 0)
        holder.tvShowMore.setOnClickListener {

        }

        val imgAdapter = ImgAdapter(context, mData[position].products)
        holder.mRecyclerView.layoutManager = GridLayoutManager(context, 3)
        holder.mRecyclerView.adapter = imgAdapter

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

    inner class ViewHolder(binding: ItemNewInStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mRecyclerView: RecyclerView = binding.mRecyclerView
        val ivStore: ImageView = binding.ivStore
        val tvName: TextView = binding.tvName
        val tvGoodsSell: TextView = binding.tvGoodsSell
        val tvShowMore: TextView = binding.tvShowMore
    }
}