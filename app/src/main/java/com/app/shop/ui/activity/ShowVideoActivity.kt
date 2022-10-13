package com.app.shop.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.app.shop.R
import com.app.shop.bean.VideoBean
import com.app.shop.databinding.ActivityShowVideoBinding
import com.app.shop.manager.Constants
import com.app.shop.util.IntentUtil
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar


/**
 * @author chenshichun
 * 创建日期：2022/10/12
 * 描述：
 */
class ShowVideoActivity : AppCompatActivity() {
    private var binding: ActivityShowVideoBinding? = null
    private lateinit var videoBean: VideoBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowVideoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        videoBean = IntentUtil.getParcelableExtra<VideoBean>(this)!!
        binding!!.viewHead.tvTitle.text = videoBean.title

        binding!!.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding!!.videoplayer.setUp(videoBean.video, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
        Glide.with(this).load(videoBean.video).centerCrop()
            .into(binding!!.videoplayer.thumbImageView)

    }

    override fun onDestroy() {
        JZVideoPlayer.releaseAllVideos();
        super.onDestroy()
    }
}