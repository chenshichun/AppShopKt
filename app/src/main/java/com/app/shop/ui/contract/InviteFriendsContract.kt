package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ShareBean

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
interface InviteFriendsContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getShareInfo(mData: BaseNetModel<ShareBean>)
    }

    interface Presenter {
        fun getShareInfo()
    }
}