package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

data class CartBean(
    val list: List<ShopBean>
)

data class ShopBean(
    val prods: ArrayList<ProdCartBean>?,
    val shop_id: Long,
    val shop_logo: String?,
    val shop_name: String?,
    var isCheck: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(ProdCartBean),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(prods)
        parcel.writeLong(shop_id)
        parcel.writeString(shop_logo)
        parcel.writeString(shop_name)
        parcel.writeByte(if (isCheck) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ShopBean(prods=$prods, shop_id=$shop_id, shop_logo=$shop_logo, shop_name=$shop_name, isCheck=$isCheck)"
    }

    companion object CREATOR : Parcelable.Creator<ShopBean> {
        override fun createFromParcel(parcel: Parcel): ShopBean {
            return ShopBean(parcel)
        }

        override fun newArray(size: Int): Array<ShopBean?> {
            return arrayOfNulls(size)
        }
    }
}

data class ProdCartBean(
    val cart_id: String?,
    val count: Int,
    val created_at: String?,
    val discount_id: String?,
    val distribution_card_no: String?,
    val img: String?,
    val is_valid: Boolean,
    val point: String?,
    val price: String?,
    val prod_id: String?,
    val prod_name: String?,
    val shop_id: String?,
    val sku_id: String?,
    val sku_name: String?,
    val updated_at: String?,
    val user_id: String?,
    var isCheck: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cart_id)
        parcel.writeInt(count)
        parcel.writeString(created_at)
        parcel.writeString(discount_id)
        parcel.writeString(distribution_card_no)
        parcel.writeString(img)
        parcel.writeByte(if (is_valid) 1 else 0)
        parcel.writeString(point)
        parcel.writeString(price)
        parcel.writeString(prod_id)
        parcel.writeString(prod_name)
        parcel.writeString(shop_id)
        parcel.writeString(sku_id)
        parcel.writeString(sku_name)
        parcel.writeString(updated_at)
        parcel.writeString(user_id)
        parcel.writeByte(if (isCheck) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ProdCartBean(cart_id=$cart_id, count=$count, created_at=$created_at, discount_id=$discount_id, distribution_card_no=$distribution_card_no, img=$img, is_valid=$is_valid, point=$point, price=$price, prod_id=$prod_id, prod_name=$prod_name, shop_id=$shop_id, sku_id=$sku_id, sku_name=$sku_name, updated_at=$updated_at, user_id=$user_id, isCheck=$isCheck)"
    }

    companion object CREATOR : Parcelable.Creator<ProdCartBean> {
        override fun createFromParcel(parcel: Parcel): ProdCartBean {
            return ProdCartBean(parcel)
        }

        override fun newArray(size: Int): Array<ProdCartBean?> {
            return arrayOfNulls(size)
        }
    }
}