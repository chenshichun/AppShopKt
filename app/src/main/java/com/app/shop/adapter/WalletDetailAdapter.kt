package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.DetailBean
import com.app.shop.databinding.ItemWalletDetailsBinding

/**
 * @author chenshichun
 * 创建日期：2022/8/30
 * 描述：
 */
class WalletDetailAdapter(private val context: Context, val mData: List<DetailBean>?) :
    RecyclerView.Adapter<WalletDetailAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWalletDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvType.text =String.format(context.getString(R.string.type),mData!![position].source)
        holder.tvContent.text = String.format(context.getString(R.string.content),mData!![position].remarks)
        holder.tvTime.text = String.format(context.getString(R.string.time),mData!![position].created_at)
        holder.tvPrice.text = String.format(context.getString(R.string.add_price),mData!![position].cash)

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

    inner class ViewHolder(binding: ItemWalletDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvType: TextView = binding.tvType
        val tvContent: TextView = binding.tvContent
        val tvTime: TextView = binding.tvTime
        val tvPrice: TextView = binding.tvPrice
    }
}