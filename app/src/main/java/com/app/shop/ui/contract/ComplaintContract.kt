package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UploadBean
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：
 */
interface ComplaintContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun upload(mData: BaseNetModel<UploadBean>)
    }

    interface Presenter {
        fun upload(file: MultipartBody.Part, description: RequestBody)
    }
}