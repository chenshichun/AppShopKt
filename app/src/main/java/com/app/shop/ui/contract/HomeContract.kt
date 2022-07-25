package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BannerBean
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ProdBean
import retrofit2.Response

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface HomeContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getBannerList(mData: BaseNetModel<BannerBean>)
        fun getProdHomeData(mData: BaseNetModel<ProdBean>)
        fun getProdFeaturedData(mData: BaseNetModel<ProdBean>)
        fun getProdRecommendData(mData: BaseNetModel<ProdBean>)
        fun sign(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun getBannerList()
        fun getProdHomeData()
        fun getProdFeaturedData()
        fun getProdRecommendData()
        fun sign()
    }
}