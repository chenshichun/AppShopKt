package com.app.shop.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityStoreHomepageBinding
import com.app.shop.ui.contract.StoreHomepageContract
import com.app.shop.ui.presenter.StoreHomepagePresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：店铺主页
 */
class StoreHomepageActivity : BaseActivity<ActivityStoreHomepageBinding, StoreHomepagePresenter>(),
    StoreHomepageContract.View {

    private lateinit var goodsAdapter: GoodsAdapter

    override fun getPresenter(): StoreHomepagePresenter {
        return StoreHomepagePresenter()
    }

    override fun initView() {
        immersionBar {
            transparentStatusBar()
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
        }

        goodsAdapter = GoodsAdapter(this, null)!!
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.mRecyclerView.adapter = goodsAdapter
        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
            }
        })
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}