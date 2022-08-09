package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.R
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.*
import com.app.shop.bean.type.CategoryType
import com.app.shop.bean.type.SortType
import com.app.shop.databinding.ActivityCategoryListBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.LoadingCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.manager.Constants
import com.app.shop.ui.contract.CategoryListContract
import com.app.shop.ui.presenter.CategoryListPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：分类列表
 */
class CategoryListActivity : BaseActivity<ActivityCategoryListBinding, CategoryListPresenter>(),
    CategoryListContract.View, View.OnClickListener {

    private var type: Int = 0
    private lateinit var goodsAdapter: GoodsAdapter
    private var goodsList = mutableListOf<Prod>()
    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10
    private var sort: String = SortType.ASC_FINAL.sortType

    override fun getPresenter(): CategoryListPresenter {
        return CategoryListPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
        type = intent.getIntExtra(Constants.CATEGORY_TYPE, 0)

        binding.viewHead.tvTitle.text =
            CategoryType.values()[type].categoryName

        binding.llComplex.setOnClickListener(this)
        binding.llSales.setOnClickListener(this)
        binding.llLowPrice.setOnClickListener(this)

        goodsAdapter = GoodsAdapter(this, goodsList)
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.mRecyclerView.adapter = goodsAdapter

        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(Intent(this@CategoryListActivity, GoodsDetailActivity::class.java))
            }
        })

        loadService = LoadSir.getDefault().register(binding.refreshLayout) {
            initData()
        }

        binding.refreshLayout.setOnRefreshListener {
            page = 1
            initData()
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page++
            initData()
        }

        initData()
    }

    private fun initData() {
        loadService.showCallback(LoadingCallback::class.java)
        when (type) {
            0 -> mPresenter!!.getProdRecommendData(page, size, sort)
            1 -> mPresenter!!.getProdFeaturedData(page, size, sort)
            else -> mPresenter!!.getProdFeaturedData(page, size, sort)
        }
    }

    /*
    * 获取的数据
    * */
    @SuppressLint("NotifyDataSetChanged")
    override fun getProdHomeData(mData: BaseNetModel<ProdBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.prods.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                goodsList.clear()
                goodsList.addAll(mData.data!!.prods)
                goodsAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.prods.isEmpty()) {
                page--
            } else {
                goodsList.addAll(mData.data!!.prods)
                goodsAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun noNetworkView() {
        loadService.showCallback(NetWorkErrorCallBack::class.java)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun showError() {
        loadService.showCallback(ErrorCallback::class.java)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ll_complex -> {
                updateSortUi(0)
                sort = SortType.ASC_PRICE.sortType
                page = 1
                initData()
            }
            R.id.ll_sales -> {
                updateSortUi(1)
                sort = SortType.DESC_SOLD.sortType
                page = 1
                initData()
            }
            R.id.ll_low_price -> {
                updateSortUi(2)
                sort = SortType.ASC_PRICE.sortType
                page = 1
                initData()
            }
        }
    }

    private fun updateSortUi(sortType: Int) {
        when (sortType) {
            0 -> {
                binding.tvComplex.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.red
                    )
                )
                binding.ivComplex.visibility = View.VISIBLE
                binding.tvSales.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.color_333
                    )
                )
                binding.ivSales.visibility = View.INVISIBLE
                binding.tvLowPrice.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.color_333
                    )
                )
                binding.ivLowPrice.visibility = View.INVISIBLE
            }
            1 -> {
                binding.tvComplex.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.color_333
                    )
                )
                binding.ivComplex.visibility = View.INVISIBLE
                binding.tvSales.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.red
                    )
                )
                binding.ivSales.visibility = View.VISIBLE
                binding.tvLowPrice.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.color_333
                    )
                )
                binding.ivLowPrice.visibility = View.INVISIBLE
            }
            2 -> {
                binding.tvComplex.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.color_333
                    )
                )
                binding.ivComplex.visibility = View.INVISIBLE
                binding.tvSales.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.color_333
                    )
                )
                binding.ivSales.visibility = View.INVISIBLE
                binding.tvLowPrice.setTextColor(
                    ContextCompat.getColor(
                        this@CategoryListActivity,
                        R.color.red
                    )
                )
                binding.ivLowPrice.visibility = View.VISIBLE
            }
        }
    }
}