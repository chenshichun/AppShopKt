package com.app.shop.ui.contract

import android.content.Context
import com.app.shop.base.BaseContract
import com.app.shop.bean.AddressInfoPO
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.ServiceApplyReq

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：
 *
 */
interface OperationCenterApplyContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun serviceApply(mData: BaseNetModel<Any>)
        fun initAddressPicker(
            provinceItems: MutableList<AddressInfoPO>,
            cityItems: MutableList<MutableList<AddressInfoPO>>,
            areaItems: MutableList<MutableList<MutableList<AddressInfoPO>>>
        )
    }

    interface Presenter {
        fun serviceApply(serviceApplyReq: ServiceApplyReq)
        fun initAddressPicker(context: Context)
    }
}