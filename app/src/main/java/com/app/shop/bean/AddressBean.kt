package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

data class AddressBean(
    val addr_list: List<Addr>
)

data class Addr(
    val addr: String?,
    val addr_id: String?,
    val area: String?,
    val area_id: String?,
    val city: String?,
    val city_id: String?,
    val created_at: String?,
    val is_default: Boolean,
    val mobile: String?,
    val post_code: String?,
    val province: String?,
    val province_id: String?,
    val receiver: String?,
    val status: Int,
    val updated_at: String?,
    val user_id: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
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
        parcel.readInt(),
        parcel.readString(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(addr)
        parcel.writeString(addr_id)
        parcel.writeString(area)
        parcel.writeString(area_id)
        parcel.writeString(city)
        parcel.writeString(city_id)
        parcel.writeString(created_at)
        parcel.writeByte(if (is_default) 1 else 0)
        parcel.writeString(mobile)
        parcel.writeString(post_code)
        parcel.writeString(province)
        parcel.writeString(province_id)
        parcel.writeString(receiver)
        parcel.writeInt(status)
        parcel.writeString(updated_at)
        parcel.writeLong(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Addr> {
        override fun createFromParcel(parcel: Parcel): Addr {
            return Addr(parcel)
        }

        override fun newArray(size: Int): Array<Addr?> {
            return arrayOfNulls(size)
        }
    }
}