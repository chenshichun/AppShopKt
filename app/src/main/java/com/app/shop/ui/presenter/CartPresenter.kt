package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.bean.type.SortType
import com.app.shop.manager.Constants
import com.app.shop.req.CartAddReq
import com.app.shop.req.CartIdReq
import com.app.shop.req.CartReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.CartContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class CartPresenter : BasePresenter<CartContract.View>(),
    CartContract.Presenter {
    override fun getCartData() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).getCartData()
            if (response.body() == null) {
                mView!!.noNetworkView()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.getCartData(response.body()!!)
                } else {
                    mView!!.showError()
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun getGoodsData(page: Int, size: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).getProdRecommendData(
                page, size,
                SortType.ASC_FINAL.sortType
            )
            if (response.body() == null) {
                mView!!.noNetworkView()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.getGoodsData(response.body()!!)
                } else {
                    mView!!.showError()
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun cartDel(cartReq: CartReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).cartDel(cartReq)
            if (response.body() == null) {
                mView!!.noNetworkView()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.cartDel(response.body()!!)
                } else {
                    mView!!.showError()
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun cartAdd(cartAddReq: CartAddReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).cartAdd(cartAddReq)
            if (response.body() == null) {
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.cartAdd(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}