package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

data class OrderInfoBean(
    val goodsName: String?,
    val goodsPoint: String?,
    val goodsPrice: String?,
    val ivGoods: String?,
    val orderId: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(goodsName)
        parcel.writeString(goodsPoint)
        parcel.writeString(goodsPrice)
        parcel.writeString(ivGoods)
        parcel.writeString(orderId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderInfoBean> {
        override fun createFromParcel(parcel: Parcel): OrderInfoBean {
            return OrderInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<OrderInfoBean?> {
            return arrayOfNulls(size)
        }
    }
}