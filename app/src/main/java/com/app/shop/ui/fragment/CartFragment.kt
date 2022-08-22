package com.app.shop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.CartAdapter
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.Prod
import com.app.shop.bean.ProdBean
import com.app.shop.databinding.FragmentCartBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.ui.contract.CartContract
import com.app.shop.ui.presenter.CartPresenter
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：购物车
 *
 */
class CartFragment : BaseFragment<FragmentCartBinding, CartPresenter>(),
    CartContract.View {
    private lateinit var cartAdapter: CartAdapter
    private var isEdit: Boolean = true //  true 编辑  false 完成
    private lateinit var goodsAdapter: GoodsAdapter

    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10
    private var goodsList = mutableListOf<Prod>()

    override fun getPresenter(): CartPresenter {
        return CartPresenter()
    }

    override fun initView() {
        // 购物车
        cartAdapter = activity?.let { CartAdapter(it, null) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = cartAdapter
        cartAdapter.setOnItemClickListener(object : CartAdapter.OnItemClickLisenter {
            override fun onItemClick(position: Int) {

            }
        })

        // 猜你喜欢
        goodsAdapter = activity?.let { GoodsAdapter(it, goodsList) }!!
        binding.goodsRv.layoutManager = GridLayoutManager(activity, 2)
        binding.goodsRv.adapter = goodsAdapter
        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
            }
        })

        binding.tvEdit.setOnClickListener {
            if (isEdit) {
                binding.tvEdit.text = "完成"
                binding.llTotal.visibility = View.GONE
                binding.tvConfirm.text = "删除"
            } else {
                binding.tvEdit.text = "编辑"
                binding.llTotal.visibility = View.VISIBLE
                binding.tvConfirm.text = "提交订单"
            }
            isEdit = !isEdit
        }

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
        mPresenter!!.getGoodsData(page, size)
    }

    override fun getGoodsData(mData: BaseNetModel<ProdBean>) {
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

    override fun showError() {
        loadService.showCallback(ErrorCallback::class.java)
    }


    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}