package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author chenshichun
 * 创建日期：2022/7/20
 * 描述：
 */
data class ProdBean(
    val all_rows: Int,//总条数
    val cur_rows: Int,//当前条数
    val prods: List<Prod>
)

data class Prod(
    val brief: String?,
    val category_id: Int,
    val content: List<String>?,
    val create_time: String?,
    val created_at: String?,
    val delivery_mode: String?,
    val delivery_template_id: Int,
    val imgs: List<String>?,
    val is_featured: Boolean,
    val is_home: Boolean,
    val is_locked: Boolean,
    val is_recommend: Boolean,
    val ori_point: String?,
    val ori_price: String?,
    val pic: String?,
    val price: String?,
    val prod_id: String?,
    val prod_name: String?,
    val prod_type: Int,
    val putaway_time: String?,
    val shop_id: Long,
    val sold_num: Int,
    val status: Int,
    val total_stocks: Int,
    val update_time: String?,
    val updated_at: String?,
    val version: Int,
    val views_count: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createStringArrayList(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(brief)
        parcel.writeInt(category_id)
        parcel.writeStringList(content)
        parcel.writeString(create_time)
        parcel.writeString(created_at)
        parcel.writeString(delivery_mode)
        parcel.writeInt(delivery_template_id)
        parcel.writeStringList(imgs)
        parcel.writeByte(if (is_featured) 1 else 0)
        parcel.writeByte(if (is_home) 1 else 0)
        parcel.writeByte(if (is_locked) 1 else 0)
        parcel.writeByte(if (is_recommend) 1 else 0)
        parcel.writeString(ori_point)
        parcel.writeString(ori_price)
        parcel.writeString(pic)
        parcel.writeString(price)
        parcel.writeString(prod_id)
        parcel.writeString(prod_name)
        parcel.writeInt(prod_type)
        parcel.writeString(putaway_time)
        parcel.writeLong(shop_id)
        parcel.writeInt(sold_num)
        parcel.writeInt(status)
        parcel.writeInt(total_stocks)
        parcel.writeString(update_time)
        parcel.writeString(updated_at)
        parcel.writeInt(version)
        parcel.writeInt(views_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Prod> {
        override fun createFromParcel(parcel: Parcel): Prod {
            return Prod(parcel)
        }

        override fun newArray(size: Int): Array<Prod?> {
            return arrayOfNulls(size)
        }
    }
}