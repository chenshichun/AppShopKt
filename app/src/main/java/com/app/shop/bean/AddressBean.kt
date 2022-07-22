package com.app.shop.bean

class AddressBean {
    var label: String? = null
    var value: String? = null
    var isStatus = false
    var children: List<CityBean>? = null

    inner class CityBean {
        var label: String? = null
        var value: String? = null
        var isStatus = false
        var children: List<AreaBean>? = null

        inner class AreaBean {
            var label: String? = null
            var value: String? = null
            var isStatus = false
        }
    }
}