package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.os.Looper
import androidx.viewpager.widget.ViewPager
import com.app.shop.adapter.MyImageAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityShowListImageBinding
import com.app.shop.ui.contract.ShowListImageContract
import com.app.shop.ui.presenter.ShowListImagePresenter
import com.app.shop.util.BitmapUtil
import com.app.shop.util.ToastUtil

/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：图片查看
 *
 */
class ShowListImageActivity : BaseActivity<ActivityShowListImageBinding, ShowListImagePresenter>(),
    ShowListImageContract.View {

    private var imageUrls = mutableListOf<String>()
    private var currentPosition = 0 // 一开始显示的照片
    var adapter: MyImageAdapter? = null

    override fun getPresenter(): ShowListImagePresenter {
        return ShowListImagePresenter()
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        imageUrls = intent.getStringArrayListExtra("paths")!!
        currentPosition = intent.getIntExtra("index", 0)

        adapter = MyImageAdapter(imageUrls, this)
        binding.viewPagerPhoto.adapter = adapter
        binding.tvImageCount.text = (currentPosition + 1).toString() + "/" + imageUrls.size
        binding.viewPagerPhoto.addOnPageChangeListener(object :
            ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                binding.tvImageCount.text = (position + 1).toString() + "/" + imageUrls.size
            }
        })
        binding.viewPagerPhoto.currentItem = currentPosition

        adapter?.setOnItemClickListener(object : MyImageAdapter.OnItemClickListener {
            override fun savePic(url: String?) {// 保存照片
                Thread {
                    Looper.prepare() //增加部分
                    BitmapUtil.returnBitmap(url)?.let {
                        BitmapUtil.saveImageToGallery(
                            applicationContext,
                            it
                        ) {
                            ToastUtil.showToast("保存成功")
                        }
                    }
                    Looper.loop() //增加部分
                }.start()
            }
        })
    }

}