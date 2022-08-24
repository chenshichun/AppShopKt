package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BannerBean
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ClassificationBean
import com.app.shop.bean.ProdBean
import retrofit2.Response
import retrofit2.http.Query

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface HomeContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getCateList(mData: BaseNetModel<ClassificationBean>)
        fun getBannerList(mData: BaseNetModel<BannerBean>)
        fun getProdHomeData(mData: BaseNetModel<ProdBean>)
        fun sign(mData: BaseNetModel<Any>)
        fun noNetworkView()
        fun showError()
    }

    interface Presenter {
        fun getCateList()
        fun getBannerList()
        fun getProdHomeData(page: Int, size: Int)
        fun sign()
    }
}