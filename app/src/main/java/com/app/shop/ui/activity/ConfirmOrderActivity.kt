package com.app.shop.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.*
import com.app.shop.databinding.ActivityConfirmOrderBinding
import com.app.shop.manager.Constants
import com.app.shop.req.CreateOrderReq
import com.app.shop.ui.contract.ConfirmOrderContract
import com.app.shop.ui.presenter.ConfirmOrderPresenter
import com.app.shop.util.IntentUtil
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：确认订单
 */
class ConfirmOrderActivity : BaseActivity<ActivityConfirmOrderBinding, ConfirmOrderPresenter>(),
    ConfirmOrderContract.View {

    private var createOrderBean = CreateOrderBean()
    private var addr_id: String? = null

    override fun getPresenter(): ConfirmOrderPresenter {
        return ConfirmOrderPresenter()
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

        createOrderBean = IntentUtil.getParcelableExtra<CreateOrderBean>(this)!!
        Glide.with(this).load(createOrderBean.pic).error(R.drawable.icon_default_pic)
            .placeholder(R.drawable.icon_default_pic).into(binding.ivGoods)
        binding.tvGoodsTitle.text = createOrderBean.prod_name
        binding.tvPrice.text =
            String.format(
                getString(if (createOrderBean.ori_point!!.toInt() != 0) R.string.goods_integral else R.string.price),
                if (createOrderBean.ori_point!!.toInt() != 0) createOrderBean.ori_point else createOrderBean.price
            )

        binding.tvTotle.text = String.format(
            getString(if (createOrderBean.ori_point!!.toInt() != 0) R.string.goods_integral else R.string.price),
            if (createOrderBean.ori_point!!.toInt() != 0) (createOrderBean.ori_point!!.toFloat() *
                    createOrderBean.count * 1.2).toString()
            else (createOrderBean.price!!.toFloat() * createOrderBean.count * 1.2).toString()
        )

        binding.tvAttr.text = String.format(getString(R.string.attr), createOrderBean.attr)
        binding.tvNum.text =
            String.format(getString(R.string.num), createOrderBean.count.toString())

        binding.tvDeliveryCost.text = createOrderBean.delivery_cost
        binding.tvServiceCost.text =
            (createOrderBean.service_cost!!.toFloat() * 100).toString() + "%"

        binding.llAddress.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.ADDR_TYPE, 1)
            IntentUtil.get()!!.goActivityResult(this, AddressListActivity::class.java, 100, bundle)
        }

        binding.tvConfirm.setOnClickListener {
            val createOrderReq = CreateOrderReq(
                addr_id,
                createOrderBean.count,
                createOrderBean.prod_id,
                createOrderBean.prod_name,
                binding.etRemark.text.toString(),
                createOrderBean.shop_id,
                createOrderBean.sku_id
            )
            mPresenter!!.orderSubmit(createOrderReq)
        }

        mPresenter!!.addrDefault()
    }

    override fun addrDefault(mData: BaseNetModel<DefaultDaarBean>) {
        addr_id = mData.data!!.addr.addr_id
        binding.tvAddrName.text = mData.data!!.addr.receiver
        binding.tvAddrPhone.text = mData.data!!.addr.mobile
        binding.tvAddr.text =
            mData.data!!.addr.province + mData.data!!.addr.city + mData.data!!.addr.area + mData.data!!.addr.addr
    }

    override fun orderSubmit(mData: BaseNetModel<OrderIdBean>) {
        val bundle = Bundle()
        val orderInfoBean = OrderInfoBean(
            createOrderBean.prod_name,
            createOrderBean.price,
            createOrderBean.pic,
            mData.data!!.order_id
        );
        IntentUtil.get()!!.goActivity(this, PayOrderActivity::class.java,orderInfoBean)
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

        /* Inserts flower into viewModel. */
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