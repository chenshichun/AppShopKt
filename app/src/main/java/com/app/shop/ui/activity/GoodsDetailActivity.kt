package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import cn.jzvd.JZVideoPlayerStandard
import com.app.shop.R
import com.app.shop.adapter.BannerAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.LunboBean
import com.app.shop.databinding.ActivityGoodsDetailBinding
import com.app.shop.ui.contract.GoodsDetailContract
import com.app.shop.ui.presenter.GoodsDetailPresenter
import com.app.shop.view.pop.SpecificationPop
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar
import com.lxj.xpopup.XPopup
import com.orhanobut.logger.Logger

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：商品详情
 */
class GoodsDetailActivity : BaseActivity<ActivityGoodsDetailBinding, GoodsDetailPresenter>(),
    GoodsDetailContract.View, View.OnClickListener {
    private var videourl = "" // 视频地址
    private val isHasVideo = false // 是否有视频
    private val mViewList = mutableListOf<View>()

    override fun getPresenter(): GoodsDetailPresenter {
        return GoodsDetailPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
        }
        binding.ivBack.setOnClickListener(this)
        binding.ivMore.setOnClickListener(this)
        binding.layoutGoodsDetailsBottom.tvBuy.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.iv_more -> {
                Logger.d("iv_more")
            }
            R.id.tv_buy -> {// 立即购买
                val specificationPop = SpecificationPop(this)
                XPopup.Builder(this)
                    .moveUpToKeyboard(false)
                    .isViewMode(true)
                    .isDestroyOnDismiss(true)
                    .asCustom(specificationPop).show()
            }
        }
    }

    @SuppressLint("InflateParams")
    fun setBannerView(lunbos: List<LunboBean>) {
        for (i in lunbos.indices) {
            if (lunbos[i].tag.equals("1")) {// 视频
                val view = LayoutInflater.from(this).inflate(R.layout.view_guide_video, null)
                val videoplayer = view.findViewById<JZVideoPlayerStandard>(R.id.videoplayer)
                videoplayer.setUp(videourl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                videoplayer.startButton.performClick()
                Glide.with(this).load(lunbos[i].url).centerCrop()
                    .into(videoplayer.thumbImageView)
                mViewList.add(view)
            } else {
                val view = LayoutInflater.from(this).inflate(R.layout.view_guide_two, null)
                val imageview = view.findViewById<ImageView>(R.id.iv_center)
                Glide.with(this).load(lunbos[i].url).into(imageview)
                mViewList.add(view)
                imageview.setOnClickListener {
                    if (isHasVideo) {
                        val img = ArrayList<String>()
                        img.add("111")
                        img.add("222")
                        val intent = Intent(this, ShowListImageActivity::class.java)
                        intent.putStringArrayListExtra("paths", img)
                        intent.putExtra("index", 123)
                        startActivity(intent)
                    } else {

                    }
                }
            }
        }
        binding.layoutGoodsDetailsBanner.viewPager.adapter = BannerAdapter(mViewList)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}