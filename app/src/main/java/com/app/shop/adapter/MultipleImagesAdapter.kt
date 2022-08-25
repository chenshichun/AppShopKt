package com.app.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.shop.R
import com.app.shop.databinding.ItemMultipleImagesBinding
import com.bumptech.glide.Glide

/**
 * @author chenshichun
 * 创建日期：2022/8/25
 * 描述：
 */
class MultipleImagesAdapter(val context: Context, val mData: List<String>?, var width: Int) :
    RecyclerView.Adapter<MultipleImagesAdapter.ViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null
    private var addImgSrc = 0
    private var maxImages = 3

    fun getMaxImages(): Int {
        return maxImages
    }

    fun setAddImg(addImgSrc: Int) {
        this.addImgSrc = addImgSrc
    }

    fun setMaxImages(maxImages: Int) {
        this.maxImages = maxImages
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMultipleImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (width != 0) {
            val params = holder.itemView.layoutParams
            params.width = width
            params.height = width
            holder.itemView.layoutParams = params
        }

        if (addImgSrc != 0) {
            holder.rlAdd.setBackgroundResource(addImgSrc)
        }

        if (mData != null && position < mData.size) {
            if (mData[position].endsWith("mp4")) {
                holder.videoIv.visibility = View.VISIBLE
            } else {
                holder.videoIv.visibility = View.GONE
            }
            Glide.with(context).load(mData[position]).placeholder(R.drawable.icon_default_pic)
                .error(R.drawable.icon_default_pic).into(holder.imgPic)

            holder.btnDelete.visibility = View.VISIBLE
            holder.btnDelete.setOnClickListener {
                mOnItemClickListener!!.onDeleteClick(position)
            }
            holder.rlPhoto.visibility = View.VISIBLE
            holder.rlAdd.visibility = View.GONE
        } else {
            holder.rlPhoto.visibility = View.GONE
            holder.rlAdd.visibility = View.VISIBLE
        }
        holder.rlAdd.setOnClickListener {
            mOnItemClickListener!!.onAddClick(position)
        }

        holder.itemView.setOnClickListener {
            mOnItemClickListener!!.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onAddClick(position: Int)
    }

    override fun getItemCount(): Int {
        val count = if (mData == null) 1 else mData.size + 1
        return if (count > maxImages) {
            mData!!.size
        } else {
            count
        }
    }

    inner class ViewHolder(binding: ItemMultipleImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rlAdd: RelativeLayout = binding.rlAdd
        val rlPhoto: RelativeLayout = binding.rlPhoto
        val imgPic: ImageView = binding.imgPic
        val videoIv: ImageView = binding.videoIv
        val btnDelete: Button = binding.btnDelete
    }

}