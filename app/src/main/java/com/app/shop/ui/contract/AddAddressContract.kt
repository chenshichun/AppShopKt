package com.app.shop.ui.contract

import android.content.Context
import com.app.shop.base.BaseContract
import com.app.shop.bean.AddressInfoPO
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.AddrReq
import retrofit2.Response
import retrofit2.http.Body

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
interface AddAddressContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun initAddressPicker(
            provinceItems: MutableList<AddressInfoPO>,
            cityItems: MutableList<MutableList<AddressInfoPO>>,
            areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>
        )

        fun addrSave(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun initAddressPicker(context: Context)

        fun addrSave(addrReq: AddrReq)
    }
}