package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.bean.PlBean
import com.app.shop.databinding.ItemPointsBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：
 *
 */
class PointAdapter(private val context: Context, val mData: List<PlBean>?) :
    RecyclerView.Adapter<PointAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPointsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = mData!![position]!!.source
        holder.tvTime.text = mData[position].created_at
        holder.tvPoint.text= mData[position].points.toString()

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

    inner class ViewHolder(binding: ItemPointsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvTitle: TextView = binding.tvTitle
        val tvPoint: TextView = binding.tvPoint
        val tvTime: TextView = binding.tvTime
    }

}