package com.app.shop.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
@Entity(tableName = "Search")
class SearchBean(
    @PrimaryKey
    var name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString().toString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchBean> {
        override fun createFromParcel(parcel: Parcel): SearchBean {
            return SearchBean(parcel)
        }

        override fun newArray(size: Int): Array<SearchBean?> {
            return arrayOfNulls(size)
        }
    }
}