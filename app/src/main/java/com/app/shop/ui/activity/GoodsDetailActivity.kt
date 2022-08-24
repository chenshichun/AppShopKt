package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewpager.widget.ViewPager
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.Prod
import com.app.shop.bean.event.PageEvent
import com.app.shop.databinding.ActivityGoodsDetailBinding
import com.app.shop.ui.contract.GoodsDetailContract
import com.app.shop.ui.presenter.GoodsDetailPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
import com.app.shop.view.GlideImageLoader
import com.app.shop.view.pop.SpecificationPop
import com.gyf.immersionbar.ktx.immersionBar
import com.lxj.xpopup.XPopup
import com.youth.banner.BannerConfig
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：商品详情
 */
class GoodsDetailActivity : BaseActivity<ActivityGoodsDetailBinding, GoodsDetailPresenter>(),
    GoodsDetailContract.View, View.OnClickListener {

    private lateinit var prod: Prod

    override fun getPresenter(): GoodsDetailPresenter {
        return GoodsDetailPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
        }

        prod = IntentUtil.getParcelableExtra<Prod>(this)!!
        updateUi(prod)

        binding.ivBack.setOnClickListener(this)
        binding.layoutGoodsDetailsBottom.tvBuy.setOnClickListener(this)
        binding.layoutGoodsDetailsMiddle.tvBuyReasonShare.setOnClickListener(this)
        binding.layoutGoodsDetailsBottom.llGoodsHome.setOnClickListener(this)
        binding.layoutGoodsDetailsBottom.llGoodsCollect.setOnClickListener(this)
        binding.layoutGoodsDetailsBottom.llGoodsCart.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_buy -> {// 立即购买
                val specificationPop = SpecificationPop(this)
                XPopup.Builder(this)
                    .moveUpToKeyboard(true)
                    .isViewMode(true)
                    .isDestroyOnDismiss(true)
                    .asCustom(specificationPop).show()
            }
            R.id.tv_buy_reason_share -> {// 分享文案
                val clipboardManager =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Label", prod.brief)
                clipboardManager.setPrimaryClip(clipData)
                ToastUtil.showToast("已复制文案")
            }
            R.id.ll_goods_home->{
                EventBus.getDefault().post(PageEvent(0))
                IntentUtil.get()!!.goActivityKill(this,MainActivity::class.java)
            }
            R.id.ll_goods_cart->{
                EventBus.getDefault().post(PageEvent(3))
                IntentUtil.get()!!.goActivityKill(this,MainActivity::class.java)
            }
            R.id.ll_goods_collect->{

            }
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    /*
    * 更新ui
    * */
    private fun updateUi(prod: Prod) {
        getBanner(prod.imgs)
        binding.layoutGoodsDetailsHead.tvIntegral.text =
            String.format(getString(R.string.goods_integral), prod.ori_point)
        binding.layoutGoodsDetailsHead.tvName.text = prod.prod_name
        binding.layoutGoodsDetailsHead.tvSell.text =
            String.format(getString(R.string.goods_sell), prod.sold_num)
        binding.layoutGoodsDetailsHead.tvPrice.text =
            String.format(getString(R.string.goods_price), prod.price)
        binding.layoutGoodsDetailsMiddle.tvBuyReason.text = prod.brief
        //htmlData(prod.content!!)
    }

    /*
    * 商品轮播图
    * */
    @SuppressLint("SetTextI18n")
    private fun getBanner(images: List<String>?) {
        binding.tvPageCount.text = "1/${images!!.size}"
        binding.banner.setImageLoader(GlideImageLoader())
        binding.banner.setImages(images)
        binding.banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
        binding.banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.tvPageCount.text = "${position + 1}/${images.size}"
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        binding.banner.setOnBannerListener { position ->
            val bundle = Bundle()
            bundle.putStringArrayList("paths", images as ArrayList<String>?)
            bundle.putInt("index", position)
            IntentUtil.get()!!.goActivity(this, GoodsDetailActivity::class.java, bundle)
        }
        binding.banner.start()
    }

    /*
     * 详情图处理
     * */
    @SuppressLint("StringFormatMatches", "SetJavaScriptEnabled")
    private fun htmlData(detailImageList: List<String>) {
        var imgBody = ""
        var detailPicBody = """<html>
<head>
<style type=\"text/css\">
body {font-size:15px;margin:1;}
</style>
</head>
<body style=margin:1;>
<script type='text/javascript'>
window.onload = function(){
var ${"$"}img = document.getElementsByTagName('img');
for(var img in  ${"$"}img){
 ${"$"}img[img].style.width = '100%%';
${"$"}img[img].style.height ='auto'
}
}
</script>%s
</body>
</html>"""
        for (url in detailImageList) {
            imgBody += java.lang.String.format(
                Locale.CHINA,
                resources.getString(R.string.detail_pic),
                url
            )
        }
        detailPicBody = String.format(Locale.CHINA, detailPicBody, imgBody)
        binding.webView.loadData(detailPicBody, "text/html", "UTF-8")
        val webSettings: WebSettings = binding.webView.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件

        //其他细节操作
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "utf-8" //设置编码格式
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
                val w = View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                )
                val h = View.MeasureSpec.makeMeasureSpec(
                    0,
                    View.MeasureSpec.UNSPECIFIED
                )
                //重新测量
                binding.webView.measure(w, h)
            }

            // 链接跳转都会走这个方法
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url) // 强制在当前 WebView 中加载 url
                return true
            }
        }
    }
}