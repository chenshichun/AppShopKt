package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.databinding.ItemSearchBinding
import com.app.shop.room.SearchBean

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
class SearchAdapter(private val context: Context, private val mData: List<SearchBean>?) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickLisenter? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickLisenter?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTv.text = mData!![position].name
        holder.deleteIv.visibility = View.GONE
        holder.itemView.setOnLongClickListener {
            holder.deleteIv.visibility = View.VISIBLE
            true
        }

        holder.deleteIv.setOnClickListener {
            mOnItemClickListener?.deleteItem(position)
        }

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickLisenter {
        fun onItemClick(position: Int)
        fun deleteItem(position: Int)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    inner class ViewHolder(binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.tvSearch
        val deleteIv: ImageView = binding.ivDelete
    }
}