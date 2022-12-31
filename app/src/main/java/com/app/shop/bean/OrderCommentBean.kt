package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

data class OrderCommentBean(
    val goods_name: String?,
    val order_id: String?,
    val pic: String?,
    val point: String?,
    val price: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(goods_name)
        parcel.writeString(order_id)
        parcel.writeString(pic)
        parcel.writeString(point)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderCommentBean> {
        override fun createFromParcel(parcel: Parcel): OrderCommentBean {
            return OrderCommentBean(parcel)
        }

        override fun newArray(size: Int): Array<OrderCommentBean?> {
            return arrayOfNulls(size)
        }
    }
}