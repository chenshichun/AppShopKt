package com.app.shop.req

/**
 * @author chenshichun
 * 创建日期：2022/7/21
 * 描述：
 *
 */
class MerchantSettledReq(
    var m_name: String? = null,
    var m_type: Int=0,
    var m_intro: String? = null,
    var legal_name: String? = null,
    var legal_phone: String? = null,
    var service_phone: String? = null,
    var m_addr_detail: String? = null,
    var m_addr_area_id: Int=0,
    var m_addr_area_full: String? = null,
    var remark: String? = null,
    var c_name: String? = null,
    var c_type: Int=0,
    var lice_num: String? = null,
    var lice_img: String? = null,
    var lice_area_id: String? = null,
    var lice_area_full: String? = null,
    var lice_area_detail: String? = null,
    var employee_name: String? = null,
    var e_type: Int=0,
    var id_type: Int=0,
    var id_num: String? = null,
    var id_card_img1: String? = null,
    var id_card_img2: String? = null,
    var id_card_img3: String? = null,
)