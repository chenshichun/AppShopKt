package com.app.shop.ui.presenter

import android.content.Context
import com.app.shop.base.BasePresenter
import com.app.shop.bean.AddressInfoPO
import com.app.shop.bean.PCACodePO
import com.app.shop.manager.Constants
import com.app.shop.req.AddrReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.AddAddressContract
import com.app.shop.util.FileUtil
import com.app.shop.util.ToastUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
class AddAddressPresenter :BasePresenter<AddAddressContract.View>(),AddAddressContract.Presenter{
    override fun initAddressPicker(context: Context) {
        val provinceItems = mutableListOf<AddressInfoPO>()
        val cityItems = mutableListOf<MutableList<AddressInfoPO>>()
        val areaItems = mutableListOf<MutableList<MutableList<AddressInfoPO>>>()
        CoroutineScope(Dispatchers.Main).launch {
            val pcaCodeList = Gson().fromJson<MutableList<PCACodePO>>(
                FileUtil.getAssetsFileText(
                    context,
                    "pcacode.json"
                ), object : TypeToken<MutableList<PCACodePO>>() {}.type
            )
            //遍历省
            pcaCodeList.forEach { pcaCode ->
                //存放省内市区
                val cityList = mutableListOf<AddressInfoPO>()
                //存放省内所有辖区
                val areaList = mutableListOf<MutableList<AddressInfoPO>>()
                //遍历省内市区
                pcaCode.children.forEach { cCode ->
                    //添加省内市区
                    cityList.add(AddressInfoPO(cCode.code, cCode.name))
                    //存放市内辖区
                    val areas = mutableListOf<AddressInfoPO>()
                    //添加市内辖区
                    cCode.children.forEach { addressInfo ->
                        areas.add(addressInfo)
                    }
                    areaList.add(areas)
                }
                //添加省份
                provinceItems.add(AddressInfoPO(pcaCode.code, pcaCode.name))
                //添加市区
                cityItems.add(cityList)
                //添加辖区
                areaItems.add(areaList)
            }
            mView!!.initAddressPicker(provinceItems, cityItems, areaItems)
        }
    }

    override fun addrSave(addrReq: AddrReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).addrSave(addrReq)
            if (response.body() == null) {
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.addrSave(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}