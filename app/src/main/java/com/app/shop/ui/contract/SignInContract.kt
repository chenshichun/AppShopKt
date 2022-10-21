package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.SignBean

/**
 * @author chenshichun
 * 创建日期：2022/10/20
 * 描述：
 */
class SignInContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun sign(mData: BaseNetModel<Any>)
        fun isSign(mData: BaseNetModel<SignBean>)
    }

    interface Presenter {
        fun sign()
        fun isSign()
    }
}