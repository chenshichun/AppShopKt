package com.app.shop.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.R
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.Prod
import com.app.shop.databinding.ActivityCollectBinding
import com.app.shop.ui.contract.CollectContract
import com.app.shop.ui.presenter.CollectPresent
import com.gyf.immersionbar.ktx.immersionBar

class CollectActivity : BaseActivity<ActivityCollectBinding, CollectPresent>(),
    CollectContract.View {
    private lateinit var goodsAdapter: GoodsAdapter
    private var goodsList: ArrayList<Prod>? = ArrayList()

    override fun getPresenter(): CollectPresent {
        return CollectPresent()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "我的收藏"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        goodsAdapter = GoodsAdapter(this, goodsList)
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.mRecyclerView.adapter = goodsAdapter

        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(Intent(this@CollectActivity, GoodsDetailActivity::class.java))
            }
        })
    }

}