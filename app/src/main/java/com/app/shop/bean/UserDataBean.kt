package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author chenshichun
 * 创建日期：2022/7/19
 * 描述：
 *
 */
class UserDataBean() :Parcelable {
    var user: UserBean? = null

    constructor(parcel: Parcel) : this() {
        user = parcel.readParcelable(UserBean::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDataBean> {
        override fun createFromParcel(parcel: Parcel): UserDataBean {
            return UserDataBean(parcel)
        }

        override fun newArray(size: Int): Array<UserDataBean?> {
            return arrayOfNulls(size)
        }
    }


}