package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

data class CartOrderBean(
    var detail: List<CartOrderDetailBean>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(CartOrderDetailBean)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartOrderBean> {
        override fun createFromParcel(parcel: Parcel): CartOrderBean {
            return CartOrderBean(parcel)
        }

        override fun newArray(size: Int): Array<CartOrderBean?> {
            return arrayOfNulls(size)
        }
    }
}

data class CartOrderDetailBean(
    val items: List<CartOrderItem>?,
    val order_id: String?,
    val order_name: String?,
    val point: String?,
    val price: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(CartOrderItem),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(items)
        parcel.writeString(order_id)
        parcel.writeString(order_name)
        parcel.writeString(point)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartOrderDetailBean> {
        override fun createFromParcel(parcel: Parcel): CartOrderDetailBean {
            return CartOrderDetailBean(parcel)
        }

        override fun newArray(size: Int): Array<CartOrderDetailBean?> {
            return arrayOfNulls(size)
        }
    }
}

data class CartOrderItem(
    val pic: String?,
    val point: String?,
    val price: String?,
    val prod_count: Int,
    val prod_name: String?,
    val sku_name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pic)
        parcel.writeString(point)
        parcel.writeString(price)
        parcel.writeInt(prod_count)
        parcel.writeString(prod_name)
        parcel.writeString(sku_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartOrderItem> {
        override fun createFromParcel(parcel: Parcel): CartOrderItem {
            return CartOrderItem(parcel)
        }

        override fun newArray(size: Int): Array<CartOrderItem?> {
            return arrayOfNulls(size)
        }
    }
}