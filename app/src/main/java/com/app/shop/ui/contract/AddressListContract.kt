package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.AddressBean
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.AddrIdReq

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 */
interface AddressListContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun addrList(mData: BaseNetModel<AddressBean>)
        fun addrDelete(mData: BaseNetModel<Any>)
        fun addrDefultSet(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun addrList()
        fun addrDelete(addrIdReq: AddrIdReq)
        fun addrDefultSet(addrIdReq: AddrIdReq)
    }
}