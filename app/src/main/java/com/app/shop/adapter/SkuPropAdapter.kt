package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.SkuInfo
import com.app.shop.bean.SkuInfoName
import com.app.shop.databinding.ItemSkuPropBinding
import com.app.shop.databinding.ItemSpecBinding

/**
 * @author chenshichun
 * 创建日期：2022/9/13
 * 描述：
 */
class SkuPropAdapter(private val context: Context, val mData: List<SkuInfoName>?) :
    RecyclerView.Adapter<SkuPropAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSkuPropBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSkuProp.text = mData!![position].name
        holder.tvSkuProp.background = ContextCompat.getDrawable(
            context,
            if (mData[position].isCheck) R.drawable.specific_attr_selected else R.drawable.specific_attr_unselected
        )
        
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position1: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemSkuPropBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvSkuProp: TextView = binding.tvSkuProp
    }
}