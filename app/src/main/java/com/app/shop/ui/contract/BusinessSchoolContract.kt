package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CollegeListBean

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：
 */
interface BusinessSchoolContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getCollegeList(mData: BaseNetModel<CollegeListBean>)
    }

    interface Presenter {
        fun getCollegeList()
    }
}