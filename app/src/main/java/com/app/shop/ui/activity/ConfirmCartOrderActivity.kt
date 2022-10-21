package com.app.shop.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.ConfirmCartAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CartOrderBean
import com.app.shop.bean.DefaultDaarBean
import com.app.shop.bean.ShopBean
import com.app.shop.databinding.ActivityConfirmCartOrderBinding
import com.app.shop.manager.Constants
import com.app.shop.req.CartAddReq
import com.app.shop.req.CartIdReq
import com.app.shop.ui.contract.ConfirmCartOrderContract
import com.app.shop.ui.presenter.ConfirmCartOrderPresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger

/**
 * @author chenshichun
 * 创建日期：2022/10/10
 * 描述：购物车订单确认
 */
class ConfirmCartOrderActivity :
    BaseActivity<ActivityConfirmCartOrderBinding, ConfirmCartOrderPresenter>(),
    ConfirmCartOrderContract.View {
    private var cartListChecked = mutableListOf<ShopBean>()
    private lateinit var confirmCartAdapter: ConfirmCartAdapter
    private var addr_id: String? = null
    private var totalPrice: String = ""
    private var allCartId: String = ""

    override fun getPresenter(): ConfirmCartOrderPresenter {
        return ConfirmCartOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "确认订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        cartListChecked = intent.getParcelableArrayListExtra<ShopBean>(Constants.CART_LIST)!!
        totalPrice = intent.getStringExtra(Constants.PRICE)!!
        binding.tvTotle.text = totalPrice

        confirmCartAdapter = ConfirmCartAdapter(this, cartListChecked)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = confirmCartAdapter

        binding.radiobutton1.setOnCheckedChangeListener { _, b ->
            binding.llAddress.visibility = if (b) View.GONE else View.VISIBLE
        }

        binding.llAddress.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.ADDR_TYPE, 1)
            IntentUtil.get()!!.goActivityResult(this, AddressListActivity::class.java, 100, bundle)
        }
        mPresenter!!.addrDefault()

        binding.tvConfirm.setOnClickListener {
            getCartId()
            val cartIdReq = CartIdReq(
                allCartId,
                if (binding.radiobutton1.isChecked) "自提" else "快递",
                addr_id,
                binding.etRemark.text.toString()
            )
            mPresenter!!.orderSubmitCart(cartIdReq)
        }
    }

    private fun getCartId() {
        allCartId = ""
        for (cartBean in cartListChecked) {
            for (item in cartBean.prods!!) {
                if (item.isCheck) {
                    allCartId = item.cart_id + "," + allCartId
                }
            }
        }
        if (allCartId.isNotEmpty())
            allCartId = allCartId.substring(0, allCartId.length - 1)
    }

    @SuppressLint("SetTextI18n")
    override fun addrDefault(mData: BaseNetModel<DefaultDaarBean>) {
        addr_id = mData.data!!.addr.addr_id
        binding.tvAddrName.text = mData.data!!.addr.receiver
        binding.tvAddrPhone.text = mData.data!!.addr.mobile
        binding.tvAddr.text =
            mData.data!!.addr.province + mData.data!!.addr.city + mData.data!!.addr.area + mData.data!!.addr.addr
    }

    override fun orderSubmitCart(mData: BaseNetModel<CartOrderBean>) {
        Logger.d(mData.data)
        IntentUtil.get()!!.goActivity(this, PayCartOrderActivity::class.java, mData.data)
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                binding.tvAddrName.text = data.getStringExtra(Constants.ADDR_NAME)
                binding.tvAddrPhone.text = data.getStringExtra(Constants.ADDR_PHONE)
                binding.tvAddr.text = data.getStringExtra(Constants.ADDR_ADDR)
                addr_id = data.getStringExtra(Constants.ADDR_ID)
            }
        }
    }
}