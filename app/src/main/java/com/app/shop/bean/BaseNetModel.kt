package com.app.shop.bean

class BaseNetModel<T> {
    var code = 0
    var msg: String? = null
    var data: T? = null
        private set
}