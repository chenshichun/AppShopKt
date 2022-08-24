package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.CateBean
import com.app.shop.databinding.ItemLeftRecycleBinding

/**
 * @author chenshichun
 * 创建日期：2022/7/6
 * 描述：
 */
class PrimaryClassificationAdapter(private val context: Context, val mData: List<CateBean>?) :
    RecyclerView.Adapter<PrimaryClassificationAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLeftRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewCheck.visibility = if (mData!![position].isCheck) View.VISIBLE else View.GONE
        holder.nameTv.setTextColor(
            ContextCompat.getColor(
                context,
                if (mData[position].isCheck) R.color.blue else R.color.color_333
            )
        )
        holder.nameTv.background = ContextCompat.getDrawable(
            context,
            if (mData[position].isCheck) R.color.color_bg else R.color.white
        )
        holder.nameTv.text = mData[position].category_name
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

    inner class ViewHolder(binding: ItemLeftRecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.tvName
        val viewCheck: View = binding.viewCheck
    }

}