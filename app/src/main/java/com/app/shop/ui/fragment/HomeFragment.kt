package com.app.shop.ui.fragment

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.app.shop.R
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.adapter.MyAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.databinding.FragmentHomeBinding
import com.app.shop.ui.activity.GoodsDetailActivity
import com.app.shop.ui.activity.SearchActivity
import com.app.shop.ui.contract.HomeContract
import com.app.shop.ui.presenter.HomePresenter
import com.app.shop.util.ToastUtil
import com.desmond.citypicker.bin.CityPicker
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import java.util.*
import kotlin.collections.ArrayList


/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：首页
 *
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePresenter>(), HomeContract.View {

    private val texts = arrayOf(
        "积分易货", "福利专区", "生活服务", "新人专区",
        "签到", "助农扶农", "红木家具", "汽车", "房产", "二手市场"
    )
    private val images = arrayOf(
        R.drawable.icon_1,
        R.drawable.icon_2,
        R.drawable.icon_3,
        R.drawable.icon_4,
        R.drawable.icon_5,
        R.drawable.icon_6,
        R.drawable.icon_7,
        R.drawable.icon_8,
        R.drawable.icon_9,
        R.drawable.icon_10
    )
    private lateinit var goodsAdapter: GoodsAdapter
    private var goodsList: ArrayList<String>? = ArrayList()
    private lateinit var mLocationClient: AMapLocationClient
    private lateinit var mLocationOption: AMapLocationClientOption

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        binding.gridView.adapter = MyAdapter(activity, images, texts)

        goodsAdapter = activity?.let { GoodsAdapter(it, goodsList) }!!
        binding.mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.mRecyclerView.adapter = goodsAdapter

        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(Intent(activity, GoodsDetailActivity::class.java))
            }
        })

        binding.mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            binding.ivTop.visibility = if (scrollY > 1000) View.VISIBLE else View.GONE
        })

        binding.ivTop.setOnClickListener {
            binding.mNestedScrollView.scrollY = 0
        }

        binding.tvSearch.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        binding.tvAddress.setOnClickListener {
            getCityData()
        }
        getCurrentLocationLatLng()
        getLocation()
    }

    /*
     * 获取定位
     * */
    @SuppressLint("CheckResult")
    private fun getLocation() {
        RxPermissions(this)
            .request(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)
            .subscribe { granted: Boolean ->
                if (granted) {
                    mLocationClient.startLocation()
                } else {
                    ToastUtil.showToast("需要定位权限")
                }
            }
    }

    /**
     * 初始化定位并设置定位回调监听
     */
    private fun getCurrentLocationLatLng() {
        //初始化定位
        mLocationClient = AMapLocationClient(context)
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener)
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()
        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
        mLocationOption.interval = 1000
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption)
    }

    /**
     * 定位回调监听器
     */
    private var mLocationListener =
        AMapLocationListener { amapLocation ->
            if (amapLocation != null) {
                if (amapLocation.errorCode == 0) {
                    //获取纬度
                    val currentLat = amapLocation.latitude
                    //获取经度
                    val currentLon = amapLocation.longitude


                    val location = Location("")
                    location.longitude = currentLon
                    location.latitude = currentLat
                    binding.tvAddress.text = getAddress(location)!![0].locality

                    mLocationClient.stopLocation()
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Logger.e(
                        "location Error, ErrCode:"
                                + amapLocation.errorCode + ", errInfo:"
                                + amapLocation.errorInfo
                    )
                }
            }
        }

    //获取地址信息:城市、街道等信息
    private fun getAddress(location: Location?): List<Address>? {
        var result: List<Address>? = null
        try {
            if (location != null) {
                val gc = Geocoder(activity, Locale.getDefault())
                result = gc.getFromLocation(
                    location.latitude,
                    location.longitude, 1
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /*
    * 选择地址
    * */
    @SuppressLint("CheckResult")
    private fun getCityData() {
        RxPermissions(this)
            .request(permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted: Boolean ->
                if (granted) {
                    CityPicker.setGpsCityByAMap("义乌", "025")
                    CityPicker.with(context)
                        //是否需要显示当前城市,如果为false那么就隐藏当前城市，并且调用setGpsCityByBaidu()或setGpsCityByAMap()都不会生效，非必选项,默认为true
                        .setUseGpsCity(true)
                        //自定义热门城市，输入数据库中的城市id（_id），非必选项，默认为数据库中的热门城市
                        .setHotCitiesId("299", "98", "146")
                        // 设置标题栏背景，非必选项
                        .setTitleBarDrawable(R.color.white)
                        // 设置搜索框背景，非必选项
                        .setSearchViewDrawable(R.color.color_bg)
                        // 设置搜索框字体颜色，非必选项
                        .setSearchViewTextColor(R.color.color_333)
                        // 设置右边检索栏字体颜色，非必选项
                        .setIndexBarTextColor(R.color.color_333)
                        // 设置返回按钮图片，非必选项
                        .setTitleBarBackBtnDrawable(R.drawable.icon_back)
                        .setUseImmerseBar(false)
                        .setMaxHistory(0)
                        .setOnCityPickerCallBack {
                            binding.tvAddress.text = it.cityName
                        }
                        .setUseGpsCity(true)
                        .open()
                } else {
                    ToastUtil.showToast("需要权限")
                }
            }
    }


    override fun getPresenter(): HomePresenter {
        return HomePresenter()
    }
}