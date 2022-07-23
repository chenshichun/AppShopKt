package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import com.app.shop.bean.UserDataBean
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface MineContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun upload(mData: BaseNetModel<UploadBean>)
        fun getMyInfo(mData: BaseNetModel<UserDataBean>)
    }

    interface Presenter {
        fun upload(file: MultipartBody.Part, description: RequestBody)
        fun getMyInfo()
    }
}