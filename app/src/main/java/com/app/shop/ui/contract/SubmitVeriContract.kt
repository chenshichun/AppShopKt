package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.AliPayDataBean
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.SubmitVeriReq

/**
 * @author chenshichun
 * 创建日期：2022/10/11
 * 描述：
 */
class SubmitVeriContract {
    interface View : BaseContract.BaseView {
        fun submitVeri(mData: BaseNetModel<AliPayDataBean>)
    }

    interface Presenter {
        fun submitVeri(submitVeriReq: SubmitVeriReq)
    }
}