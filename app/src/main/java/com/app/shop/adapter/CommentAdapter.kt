package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.bean.CommInfo
import com.app.shop.databinding.ItemCommentBinding
import com.bumptech.glide.Glide
import me.zhanghai.android.materialratingbar.MaterialRatingBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：
 */
class CommentAdapter(private val context: Context, val mData: List<CommInfo>?) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCommentName.text = mData!![position].name

        Glide.with(context).load(mData[position].profile_pic)
            .placeholder(R.drawable.icon_default_pic)
            .error(R.drawable.icon_default_pic)
            .into(holder.ivCommentHead)

        holder.tvCommentTime.text = mData[position].created_at
        holder.tvCommentContent.text = mData[position].content
        holder.rbCommentScore.rating = mData[position].score.toFloat()

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

    inner class ViewHolder(binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivCommentHead: ImageView = binding.ivCommentHead
        val tvCommentName: TextView = binding.tvCommentName
        val tvCommentContent: TextView = binding.tvCommentContent
        val tvCommentTime: TextView = binding.tvCommentTime
        val rbCommentScore: MaterialRatingBar = binding.rbCommentScore
    }

}