package com.app.shop.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：
 *
 */
class MyImageAdapter(private val imageUrls: List<String>, val activity: AppCompatActivity) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val url = imageUrls[position]
        val photoView = PhotoView(activity)
        Glide.with(activity).load(url).into(photoView)
        container.addView(photoView)
        photoView.setOnClickListener {
            activity.finish()
        }

        photoView.setOnLongClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("确定要保存照片吗")
            builder.setTitle("提示")
            builder.setPositiveButton(
                "确定"
            ) { dialog, _ ->
                onItemClickListener!!.savePic(url)
                dialog.dismiss()
            }
            builder.setNegativeButton(
                "取消"
            ) { dialog, _ -> dialog.dismiss() }
            builder.create().show()

            false
        }
        return photoView
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun savePic(url: String?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {   //实现点击
        this.onItemClickListener = onItemClickListener
    }
}