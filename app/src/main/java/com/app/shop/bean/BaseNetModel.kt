package com.app.shop.bean

class BaseNetModel<T> {
    var code = 0
    var message: String? = null
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }
}