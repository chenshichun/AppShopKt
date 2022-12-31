package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.app.shop.R
import com.app.shop.adapter.ViewPagerAdapter
import com.app.shop.databinding.ActivityWelcomeGuideBinding
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ImmersionBar

class WelcomeGuideActivity : AppCompatActivity() {
    private var binding: ActivityWelcomeGuideBinding? = null
    private var mViewList = arrayListOf<View>()
    private lateinit var goTv: TextView
    private var mPaintDis = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeGuideBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        ImmersionBar.with(this)
            .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
            .init()

        initData()
        binding!!.mViewPager.adapter = ViewPagerAdapter(mViewList)

        binding!!.ivRed.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    //避免重复回调        出于兼容性考虑，使用了过时的方法
                    binding!!.ivRed.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    //布局完成了就获取第一个小灰点和第二个之间left的距离
                    mPaintDis =
                        binding!!.llContainer.getChildAt(1).left - binding!!.llContainer.getChildAt(
                            0
                        ).left
                }
            })

        //ViewPager滑动Pager监听
        binding!!.mViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            //滑动过程中的回调
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //当滑到第二个Pager的时候，positionOffset百分比会变成0，position会变成1，所以后面要加上position*mPaintDis
                val leftMargin = (mPaintDis * positionOffset).toInt() + position * mPaintDis
                //在父布局控件中设置他的leftMargin边距
                val params = binding!!.ivRed.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = leftMargin
                binding!!.ivRed.layoutParams = params
            }

            /**
             * 设置按钮最后一页显示，其他页面隐藏
             * @param position
             */
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {
                println("state:$state")
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun initData() {
        mViewList = ArrayList()
        val view1 = layoutInflater.inflate(R.layout.we_indicator1, null)
        val view2 = layoutInflater.inflate(R.layout.we_indicator2, null)
        val view3 = layoutInflater.inflate(R.layout.we_indicator3, null)
        mViewList.add(view1)
        mViewList.add(view2)
        mViewList.add(view3)

        for (i in 0 until mViewList.size + 1) {
            //小圆点
            val pointView = ImageView(this)
            pointView.setImageResource(R.drawable.shape_point1)
            //初始化布局参数，父控件是谁，就初始化谁的布局参数
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            if (i > 0) {
                //当添加的小圆点的个数超过一个的时候就设置当前小圆点的左边距为20dp;
                params.leftMargin = 20
            }
            //设置小灰点的宽高包裹内容
            pointView.layoutParams = params
            //将小灰点添加到LinearLayout中
            binding!!.llContainer.addView(pointView)
        }

        goTv = view3.findViewById(R.id.tv_go)
        goTv.setOnClickListener {
            IntentUtil.get()!!.goActivity(this, AccountLoginActivity::class.java)
            finish()
        }
    }
}