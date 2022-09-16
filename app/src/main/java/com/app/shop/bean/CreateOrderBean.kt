package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author chenshichun
 * 创建日期：2022/9/14
 * 描述：
 */
class CreateOrderBean() : Parcelable {
    var prod_id: String? = null
    var prod_name: String? = null
    var sku_id: String? = null
    var shop_id: String? = null
    var count: Int = 0
    var addr_id: String? = null
    var remark: String? = null
    var pic: String? = null
    var attr: String? = null
    var price: String? = null
    var ori_point: String? = null
    var delivery_cost: String? = null// 邮费
    var service_cost: String? = null// 服务费

    constructor(parcel: Parcel) : this() {
        prod_id = parcel.readString()
        prod_name = parcel.readString()
        sku_id = parcel.readString()
        shop_id = parcel.readString()
        count = parcel.readInt()
        addr_id = parcel.readString()
        remark = parcel.readString()
        pic = parcel.readString()
        attr = parcel.readString()
        price = parcel.readString()
        ori_point = parcel.readString()
        delivery_cost = parcel.readString()
        service_cost = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(prod_id)
        parcel.writeString(prod_name)
        parcel.writeString(sku_id)
        parcel.writeString(shop_id)
        parcel.writeInt(count)
        parcel.writeString(addr_id)
        parcel.writeString(remark)
        parcel.writeString(pic)
        parcel.writeString(attr)
        parcel.writeString(price)
        parcel.writeString(ori_point)
        parcel.writeString(delivery_cost)
        parcel.writeString(service_cost)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreateOrderBean> {
        override fun createFromParcel(parcel: Parcel): CreateOrderBean {
            return CreateOrderBean(parcel)
        }

        override fun newArray(size: Int): Array<CreateOrderBean?> {
            return arrayOfNulls(size)
        }
    }

}