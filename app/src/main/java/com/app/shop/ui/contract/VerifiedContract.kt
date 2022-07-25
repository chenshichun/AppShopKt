package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.CertReq

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：
 *
 */
interface VerifiedContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun cert(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun cert(certReq: CertReq)
    }
}