package com.app.shop.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.CartAdapter
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.bean.*
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
    CartContract.View, View.OnClickListener {
    private lateinit var cartAdapter: CartAdapter
    private var isEdit: Boolean = true //  true 编辑  false 完成
    private lateinit var goodsAdapter: GoodsAdapter

    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10
    private var goodsList = mutableListOf<Prod>()
    private var cartList = mutableListOf<ShopBean>()

    override fun getPresenter(): CartPresenter {
        return CartPresenter()
    }

    override fun initView() {
        binding.checkbox.setOnClickListener(this)
        // 购物车
        cartAdapter = activity?.let { CartAdapter(it, cartList) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = cartAdapter
        cartAdapter.setOnItemClickListener(object : CartAdapter.OnItemClickListener {

            override fun onDetailClick(position: Int, position1: Int) {// 跳转商品详情页
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun checkClick(position: Int) {// 店铺选择
                cartList[position].isCheck = !cartList[position].isCheck
                for (prodBean in cartList[position].prods) {
                    prodBean.isCheck = cartList[position].isCheck
                }
                cartAdapter.notifyDataSetChanged()
                judgeCheckAll()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun checkClick(position: Int, position1: Int) {//  商品选择
                cartList[position].prods[position1].isCheck =
                    !cartList[position].prods[position1].isCheck

                var isShopCheck = true
                for (cartGoodsBean in cartList[position].prods) {
                    if (!cartGoodsBean.isCheck) {
                        isShopCheck = false
                    }
                }
                cartList[position].isCheck = isShopCheck

                cartAdapter.notifyDataSetChanged()
                judgeCheckAll()
            }

            override fun deleteClick(position: Int, position1: Int) {

            }

            override fun reduceClick(position: Int, position1: Int) {
            }

            override fun addClick(position: Int, position1: Int) {
            }

            override fun modifyClick(position: Int, position1: Int) {
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
            mPresenter!!.getGoodsData(page, size)
        }
        initData()

    }

    private fun initData() {
        mPresenter!!.getGoodsData(page, size)
        mPresenter!!.getCartData()
    }

    /*
    * 购物车数据
    * */
    override fun getCartData(mData: BaseNetModel<CartBean>) {
        cartList.clear()
        cartList.addAll(mData.data!!.list)
        cartAdapter.notifyDataSetChanged()
    }

    /*
    * 猜你喜欢
    * */
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

    /*
    * 判断是否全选
    * */
    private fun judgeCheckAll() {
        var isAllCheck = true
        for (cartBean in cartList) {
            if (!cartBean.isCheck) {
                isAllCheck = false
            }
        }
        binding.checkbox.isChecked = isAllCheck
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.checkbox -> {
                for (cartBean in cartList) {
                    cartBean.isCheck = binding.checkbox.isChecked
                    for (cartGoodsBean in cartBean.prods) {
                        cartGoodsBean.isCheck = binding.checkbox.isChecked
                    }
                }
                cartAdapter.notifyDataSetChanged()
            }
        }
    }
}