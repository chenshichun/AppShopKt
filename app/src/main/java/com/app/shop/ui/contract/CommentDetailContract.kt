package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CommentBean

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：
 *
 */
interface CommentDetailContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun prodCommAll(mData: BaseNetModel<CommentBean>)
    }

    interface Presenter {
        fun prodCommAll(id: String)
    }
}