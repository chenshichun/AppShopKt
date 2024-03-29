package com.app.shop.view

import android.content.Context
import android.widget.ImageView
import com.app.shop.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.loader.ImageLoader

/**
 * @author chenshichun
 * 创建日期：2022/7/21
 * 描述：
 *
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView?) {
        imageView?.let {
            Glide.with(context)
                .load(path)
                .placeholder(R.drawable.icon_default_pic)
                .error(R.drawable.icon_default_pic)
                .into(it)
        }
    }
}