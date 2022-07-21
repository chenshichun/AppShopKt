package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.req.PwdLoginReq

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：
 *
 */
interface AccountLoginContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun pwdLogin(mData: BaseNetModel<UserDataBean>)
    }

    interface Presenter {
        fun pwdLogin(pwdLoginReq: PwdLoginReq)
    }
}