package com.app.shop.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.app.shop.manager.Constants
import com.app.shop.req.CartAddReq
import com.app.shop.req.CartReq
import com.app.shop.ui.activity.ConfirmCartOrderActivity
import com.app.shop.ui.activity.GoodsDetailActivity
import com.app.shop.ui.contract.CartContract
import com.app.shop.ui.presenter.CartPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
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
    private var cartListChecked = mutableListOf<ShopBean>()

    private var allCartId = ""

    override fun getPresenter(): CartPresenter {
        return CartPresenter()
    }

    override fun initView() {
        binding.checkbox.setOnClickListener(this)
        binding.tvConfirm.setOnClickListener(this)

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
                for (prodBean in cartList[position].prods!!) {
                    prodBean.isCheck = cartList[position].isCheck
                }
                cartAdapter.notifyDataSetChanged()
                judgeCheckAll()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun checkClick(position: Int, position1: Int) {//  商品选择
                cartList[position].prods?.get(position1)!!.isCheck =
                    !cartList[position].prods?.get(position1)!!.isCheck

                var isShopCheck = true
                for (cartGoodsBean in cartList[position].prods!!) {
                    if (!cartGoodsBean.isCheck) {
                        isShopCheck = false
                    }
                }
                cartList[position].isCheck = isShopCheck

                cartAdapter.notifyDataSetChanged()
                judgeCheckAll()
            }

            override fun deleteClick(position: Int, position1: Int) {
                mPresenter!!.cartDel(CartReq(cartList[position].prods!![position1].cart_id!!))
            }

            override fun reduceClick(position: Int, position1: Int) {
                if (cartList[position].prods!![position1].count > 1) {

                    val cartAddReq = CartAddReq(
                        cartList[position].prods!![position1].cart_id!!,
                        cartList[position].prods!![position1].count - 1, "", ""
                    )
                    mPresenter!!.cartAdd(cartAddReq)
                } else {
                    ToastUtil.showToast("不能再减了~")
                }
            }

            override fun addClick(position: Int, position1: Int) {
                val cartAddReq = CartAddReq(
                    cartList[position].prods!![position1].cart_id!!,
                    cartList[position].prods!![position1].count + 1, "", ""
                )
                mPresenter!!.cartAdd(cartAddReq)
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
                val bundle = Bundle()
                bundle.putString(Constants.GOODS_ID, goodsList[position].prod_id)
                IntentUtil.get()!!
                    .goActivity(activity, GoodsDetailActivity::class.java, bundle)
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

    }

    override fun onResume() {
        initData()
        super.onResume()
    }
    private fun initData() {
        mPresenter!!.getGoodsData(page, size)
        mPresenter!!.getCartData()
    }

    /*
    * 购物车数据
    * */
    @SuppressLint("NotifyDataSetChanged")
    override fun getCartData(mData: BaseNetModel<CartBean>) {
        cartList.clear()
        cartList.addAll(mData.data!!.list)
        cartAdapter.notifyDataSetChanged()
    }

    /*
    * 猜你喜欢
    * */
    @SuppressLint("NotifyDataSetChanged")
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

    /*
    * 删除购物车
    * */
    override fun cartDel(mData: BaseNetModel<Any>) {
        mPresenter!!.getCartData()
    }

    override fun noNetworkView() {
        loadService.showCallback(NetWorkErrorCallBack::class.java)
    }

    override fun showError() {
        loadService.showCallback(ErrorCallback::class.java)
    }

    override fun cartAdd(mData: BaseNetModel<Any>) {
        mPresenter!!.getCartData()
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
        totalPrice()
    }

    private fun totalPrice() {
        var allPrice = 0.0// 总金额
        var allPoint = 0//  总积分
        allCartId = ""
        for (cartBean in cartList) {
            for (item in cartBean.prods!!) {
                if (item.isCheck) {
                    allPrice += item.price!!.toDouble() * item.count
                    allPoint += item.point!!.toInt() * item.count
                    allCartId = item.cart_id + "," + allCartId
                }
            }
        }
        if (allCartId.isNotEmpty())
            allCartId = allCartId.substring(0, allCartId.length - 1)

        if (allPoint > 0) {
            binding.tvPrice.text = if (allPrice > 0) {
                String.format(
                    getString(
                        R.string.price_goods_integral,
                        allPrice.toString(), allPoint.toString()
                    )
                )
            } else {
                String.format(getString(R.string.goods_integral, allPoint.toString()))
            }
        } else {
            binding.tvPrice.text = String.format(getString(R.string.price, allPrice.toString()))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.checkbox -> {
                for (cartBean in cartList) {
                    cartBean.isCheck = binding.checkbox.isChecked
                    for (cartGoodsBean in cartBean.prods!!) {
                        cartGoodsBean.isCheck = binding.checkbox.isChecked
                    }
                }
                cartAdapter.notifyDataSetChanged()
                totalPrice()
            }
            R.id.tv_confirm -> {
                if (allCartId.isEmpty()) {
                    ToastUtil.showToast("至少选择一款商品")
                    return
                }
                val bundle = Bundle()
                bundle.putParcelableArrayList(
                    Constants.CART_LIST,
                    cartList as ArrayList<ShopBean>
                )
                bundle.putString(Constants.PRICE, binding.tvPrice.text.toString())
                IntentUtil.get()!!
                    .goActivity(activity, ConfirmCartOrderActivity::class.java, bundle)
            }
        }
    }
}