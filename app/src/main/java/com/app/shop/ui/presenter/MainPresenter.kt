package com.app.shop.ui.presenter

import com.app.shop.base.BaseConstant
import com.app.shop.base.BasePresenter
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.HotSearchBean
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.MainContract
import com.app.shop.util.ToastUtil
import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {

    override fun testPresenter() {
        mView!!.testView("ss")
    }

    override fun hotSearch() {
        ApiRequest.create(HomeService::class.java).getHotSearch()
            .enqueue(object : Callback<BaseNetModel<ArrayList<HotSearchBean>>> {
                override fun onResponse(
                    call: Call<BaseNetModel<ArrayList<HotSearchBean>>>,
                    response: Response<BaseNetModel<ArrayList<HotSearchBean>>>
                ) {
                    if (response.body() == null) {
                        ToastUtil.showToast("无网络")
                        Logger.d("无网络")
                        return
                    }else{
                        mView!!.hotSearch(response.body()!!)
                    }
                }

                override fun onFailure(
                    call: Call<BaseNetModel<ArrayList<HotSearchBean>>>,
                    t: Throwable
                ) {
                }

            })
    }

    override fun hotNewSearch() {
    }
}