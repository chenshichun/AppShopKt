package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.TransferPointrReq

/**
 * @author chenshichun
 * 创建日期：2022/9/28
 * 描述：
 */
class ConversionIntegralContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun transferPoint(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun transferPoint(transferPointrReq: TransferPointrReq)
    }
}