package com.app.shop.ui.contract

import android.content.Context
import com.app.shop.base.BaseContract
import com.app.shop.bean.AddressInfoPO
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.req.MerchantSettledReq
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

/**
 * @author chenshichun
 * 创建日期：2022/7/19
 * 描述：
 *
 */
interface MerchantSettledContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun upload(mData: BaseNetModel<UploadBean>)
        fun merchantApply(mData: BaseNetModel<Any>)
        fun initAddressPicker(
            provinceItems: MutableList<AddressInfoPO>,
            cityItems: MutableList<MutableList<AddressInfoPO>>,
            areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>,
            type: Int
        )
    }

    interface Presenter {
        fun upload(file: MultipartBody.Part, description: RequestBody)
        fun merchantApply(merchantSettledReq: MerchantSettledReq)
        fun initAddressPicker(context: Context, type: Int)
    }
}