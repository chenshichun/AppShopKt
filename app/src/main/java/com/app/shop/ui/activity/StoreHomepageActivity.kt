package com.app.shop.ui.activity

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.R
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.databinding.ActivityStoreHomepageBinding
import com.app.shop.req.StoreIdReq
import com.app.shop.ui.contract.StoreHomepageContract
import com.app.shop.ui.presenter.StoreHomepagePresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：店铺主页
 */
class StoreHomepageActivity : BaseActivity<ActivityStoreHomepageBinding, StoreHomepagePresenter>(),
    StoreHomepageContract.View, View.OnClickListener {

    private lateinit var goodsAdapter: GoodsAdapter
    private val id: String = ""
    private var isAttention = false

    override fun getPresenter(): StoreHomepagePresenter {
        return StoreHomepagePresenter()
    }

    override fun initView() {
        immersionBar {
            transparentStatusBar()
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
        }

        goodsAdapter = GoodsAdapter(this, null)
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.mRecyclerView.adapter = goodsAdapter
        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
            }
        })

        binding.ivBack.setOnClickListener(this)
        binding.tvShopName.setOnClickListener(this)
        binding.tvAttention.setOnClickListener(this)
    }

    override fun storeAdd(mData: BaseNetModel<Any>) {
        isAttention = true
        binding.tvAttention.text = "已关注"
    }

    override fun storeDel(mData: BaseNetModel<Any>) {
        isAttention = false
        binding.tvAttention.text = "关注"
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_shop_name -> IntentUtil.get()!!
                .goActivity(this, StoreImpressionActivity::class.java)
            R.id.tv_attention -> {
                val storeIdReq = StoreIdReq(id)
                if (!isAttention) {
                    mPresenter!!.storeAdd(storeIdReq)
                }else{
                    mPresenter!!.storeDel(storeIdReq)
                }
            }
        }
    }
}