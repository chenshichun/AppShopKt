package com.app.shop.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：
 *
 */
class LunboBean() : Parcelable {
    var url: String? = null
    var tag: String? = null

    constructor(parcel: Parcel) : this() {
        url = parcel.readString()
        tag = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(tag)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LunboBean> {
        override fun createFromParcel(parcel: Parcel): LunboBean {
            return LunboBean(parcel)
        }

        override fun newArray(size: Int): Array<LunboBean?> {
            return arrayOfNulls(size)
        }
    }
}