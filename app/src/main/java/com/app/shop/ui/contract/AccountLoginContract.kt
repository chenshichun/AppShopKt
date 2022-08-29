package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.req.PwdLoginReq
import com.app.shop.req.WxLoginReq

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：
 *
 */
interface AccountLoginContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun pwdLogin(mData: BaseNetModel<UserDataBean>)
        fun wechatLogin(mData: BaseNetModel<UserDataBean>)
        fun bindPhone()
    }

    interface Presenter {
        fun pwdLogin(pwdLoginReq: PwdLoginReq)
        fun wechatLogin(wxLoginReq: WxLoginReq)
    }
}