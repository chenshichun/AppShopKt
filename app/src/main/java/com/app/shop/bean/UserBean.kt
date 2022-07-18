package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
class UserBean() : Parcelable {
    var id: String? = null
    var login: String? = null
    var nickName: String? = null
    var realName: String? = null
    var gender: String? = null
    var photo: String? = null
    var isSign = false
    var role = 0
    var callCount = 0
    var favoriteCount = 0
    var purOrderCount = 0
    var score = 0
    var phone: String? = null
    var token: String? = null
    var status: String? = null
    var province: String? = null
    var city: String? = null
    var county: String? = null
    var homeAddr: String? = null
    var birthday: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        login = parcel.readString()
        nickName = parcel.readString()
        realName = parcel.readString()
        gender = parcel.readString()
        photo = parcel.readString()
        isSign = parcel.readByte() != 0.toByte()
        role = parcel.readInt()
        callCount = parcel.readInt()
        favoriteCount = parcel.readInt()
        purOrderCount = parcel.readInt()
        score = parcel.readInt()
        phone = parcel.readString()
        token = parcel.readString()
        status = parcel.readString()
        province = parcel.readString()
        city = parcel.readString()
        county = parcel.readString()
        homeAddr = parcel.readString()
        birthday = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(login)
        parcel.writeString(nickName)
        parcel.writeString(realName)
        parcel.writeString(gender)
        parcel.writeString(photo)
        parcel.writeByte(if (isSign) 1 else 0)
        parcel.writeInt(role)
        parcel.writeInt(callCount)
        parcel.writeInt(favoriteCount)
        parcel.writeInt(purOrderCount)
        parcel.writeInt(score)
        parcel.writeString(phone)
        parcel.writeString(token)
        parcel.writeString(status)
        parcel.writeString(province)
        parcel.writeString(city)
        parcel.writeString(county)
        parcel.writeString(homeAddr)
        parcel.writeString(birthday)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserBean> {
        override fun createFromParcel(parcel: Parcel): UserBean {
            return UserBean(parcel)
        }

        override fun newArray(size: Int): Array<UserBean?> {
            return arrayOfNulls(size)
        }
    }

}