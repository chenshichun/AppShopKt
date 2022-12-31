package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.NickNameReq

/**
 * @author chenshichun
 * 创建日期：2022/7/23
 * 描述：
 *
 */
interface ModifyNickNameContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun modifyNickName(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun modifyNickName(nickNameReq: NickNameReq)
    }
}