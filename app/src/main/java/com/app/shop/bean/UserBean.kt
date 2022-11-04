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
    var created_at: String? = null
    var updated_at: String? = null
    var lv = 0
    var direct_team_count = 0
    var all_team_count = 0
    var cash : String? = null
    var coupon = 0
    var points : String? = null
    var is_certified = false
    var username: String? = null
    var date_joined: String? = null
    var nick_name: String? = null
    var phone: String? = null
    var ref_code: String? = null
    var inv_code: String? = null
    var gender: String? = null
    var profile_pic: String? = null
    var locked = false
    var isvarid = false
    var token: String? = null
    var my_qr: String? = null
    var share_qr: String? = null
    var identity: String? = null

    constructor(parcel: Parcel) : this() {
        created_at = parcel.readString()
        updated_at = parcel.readString()
        lv = parcel.readInt()
        direct_team_count = parcel.readInt()
        all_team_count = parcel.readInt()
        cash = parcel.readString()
        coupon = parcel.readInt()
        points = parcel.readString()
        is_certified = parcel.readByte() != 0.toByte()
        username = parcel.readString()
        date_joined = parcel.readString()
        nick_name = parcel.readString()
        phone = parcel.readString()
        ref_code = parcel.readString()
        inv_code = parcel.readString()
        gender = parcel.readString()
        profile_pic = parcel.readString()
        locked = parcel.readByte() != 0.toByte()
        isvarid = parcel.readByte() != 0.toByte()
        token = parcel.readString()
        my_qr = parcel.readString()
        share_qr = parcel.readString()
        identity = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
        parcel.writeInt(lv)
        parcel.writeInt(direct_team_count)
        parcel.writeInt(all_team_count)
        parcel.writeString(cash)
        parcel.writeInt(coupon)
        parcel.writeString(points)
        parcel.writeByte(if (is_certified) 1 else 0)
        parcel.writeString(username)
        parcel.writeString(date_joined)
        parcel.writeString(nick_name)
        parcel.writeString(phone)
        parcel.writeString(ref_code)
        parcel.writeString(inv_code)
        parcel.writeString(gender)
        parcel.writeString(profile_pic)
        parcel.writeByte(if (locked) 1 else 0)
        parcel.writeByte(if (isvarid) 1 else 0)
        parcel.writeString(token)
        parcel.writeString(my_qr)
        parcel.writeString(share_qr)
        parcel.writeString(identity)
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