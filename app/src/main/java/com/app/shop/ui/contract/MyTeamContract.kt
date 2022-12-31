package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.MyTeamBean
import com.app.shop.bean.TeamAllBean

/**
 * @author chenshichun
 * 创建日期：2022/8/22
 * 描述：
 *
 */
interface MyTeamContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun teamMy(mData: BaseNetModel<MyTeamBean>)
        fun teamAll(mData: BaseNetModel<TeamAllBean>)
        fun noNetworkView()
        fun showError()
    }

    interface Presenter {
        fun teamMy()
        fun teamAll(page:Int,size:Int)
    }
}