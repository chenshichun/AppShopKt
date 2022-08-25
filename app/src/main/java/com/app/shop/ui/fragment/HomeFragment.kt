package com.app.shop.ui.fragment

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Outline
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.app.shop.R
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.adapter.MyAdapter
import com.app.shop.adapter.TabAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.bean.*
import com.app.shop.bean.event.CateEvent
import com.app.shop.bean.event.PageEvent
import com.app.shop.bean.type.CategoryType
import com.app.shop.databinding.FragmentHomeBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.manager.Constants
import com.app.shop.ui.activity.CategoryListActivity
import com.app.shop.ui.activity.GoodsDetailActivity
import com.app.shop.ui.activity.OperationsCenterActivity
import com.app.shop.ui.activity.SearchActivity
import com.app.shop.ui.contract.HomeContract
import com.app.shop.ui.presenter.HomePresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
import com.app.shop.view.GlideImageLoader
import com.desmond.citypicker.bin.CityPicker
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.youth.banner.BannerConfig
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：首页
 *
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePresenter>(), HomeContract.View,
    OnItemClickListener, View.OnClickListener {
    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10
    private lateinit var register: ActivityResultLauncher<Intent>

    private val texts = arrayOf(
        "签到", "积分易货", "福利专区", "生活服务", "助农扶农",
        "红木家具", "汽车易货", "房产易货", "二手市场", "新人专区"
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
    private lateinit var tabAdapter: TabAdapter
    private lateinit var goodsAdapter: GoodsAdapter
    private var goodsList = mutableListOf<Prod>()
    private var tabsList = mutableListOf<TabBean>()

    private lateinit var mLocationClient: AMapLocationClient
    private lateinit var mLocationOption: AMapLocationClientOption

    private var cateBeanList = mutableListOf<CateBean>()


    override fun getPresenter(): HomePresenter {
        return HomePresenter()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        binding.gridView.adapter = MyAdapter(activity, images, texts)
        binding.gridView.onItemClickListener = this

        tabAdapter = activity?.let { TabAdapter(cateBeanList) }!!
        tabAdapter.setOnItemClickListener(object : TabAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                for (cateBean in cateBeanList) {
                    cateBean.isCheck = false
                }
                cateBeanList[position].isCheck = true

                tabAdapter.notifyDataSetChanged()
                if (position != 0) {
                    EventBus.getDefault().post(PageEvent(1))
                    EventBus.getDefault().post(CateEvent(position - 1))
                }
            }
        })

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.lableRv.layoutManager = layoutManager
        binding.lableRv.adapter = tabAdapter

        goodsAdapter = activity?.let { GoodsAdapter(it, goodsList) }!!
        binding.mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.mRecyclerView.adapter = goodsAdapter

        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                IntentUtil.get()!!
                    .goActivity(activity, GoodsDetailActivity::class.java, goodsList[position])
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

        binding.tvAddress.setOnClickListener {// 选择城市
            getCityData()
        }

        binding.ivClassification.setOnClickListener {// 分类
            EventBus.getDefault().post(PageEvent(1))
        }
        getCurrentLocationLatLng()
        getLocation()
        binding.ivScan.setOnClickListener(this)
        binding.layoutHomeMiddle.llNew.setOnClickListener(this)
        binding.layoutHomeMiddle.llCenter.setOnClickListener(this)
        binding.layoutHomeMiddle.llLarge.setOnClickListener(this)
        binding.layoutHomeMiddle.llSpikeBuy.setOnClickListener(this)

        loadService = LoadSir.getDefault().register(binding.refreshLayout) {
            initData()
            mPresenter!!.getBannerList()
            mPresenter!!.getCateList()
        }

        binding.refreshLayout.setOnRefreshListener {
            page = 1
            initData()
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page++
            initData()
        }
        initData()
        mPresenter!!.getBannerList()
        mPresenter!!.getCateList()

        // 注册扫描二维码
        register = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val data = it.data
            if (null != data) {
                val bundle: Bundle? = data.extras
                if (bundle!!.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    val result = bundle.getString(CodeUtils.RESULT_STRING)
                    Logger.d(result)
                    ToastUtil.showToast(result)
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showToast("解析二维码失败")
                }
            }
        }
    }

    private fun initData() {
        mPresenter!!.getProdHomeData(page, size)
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
        AMapLocationListener { ampLocation ->
            if (ampLocation != null) {
                if (ampLocation.errorCode == 0) {
                    //获取纬度
                    val currentLat = ampLocation.latitude
                    //获取经度
                    val currentLon = ampLocation.longitude


                    val location = Location("")
                    location.longitude = currentLon
                    location.latitude = currentLat
                    binding.tvAddress.text = getAddress(location)!![0].locality

                    mLocationClient.stopLocation()
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Logger.e(
                        "location Error, ErrCode:"
                                + ampLocation.errorCode + ", errInfo:"
                                + ampLocation.errorInfo
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
                        .setTitleBarDrawable(R.drawable.bg_withdraw)
                        // 设置搜索框背景，非必选项
                        .setSearchViewDrawable(R.drawable.bg_white_20)
                        // 设置搜索框字体颜色，非必选项
                        .setSearchViewTextColor(R.color.color_333)
                        // 设置右边检索栏字体颜色，非必选项
                        .setIndexBarTextColor(R.color.color_333)
                        // 设置返回按钮图片，非必选项
                        .setTitleBarBackBtnDrawable(R.drawable.icon_white_back)
                        .setUseImmerseBar(true)
                        .setMaxHistory(0)
                        .setOnCityPickerCallBack {
                            binding.tvAddress.text =
                                if (it.cityName.length > 3) (it.cityName.substring(
                                    0,
                                    3
                                ) + "...") else it.cityName
                        }
                        .setUseGpsCity(true)
                        .open()
                } else {
                    ToastUtil.showToast("需要权限")
                }
            }
    }

    override fun getCateList(mData: BaseNetModel<ClassificationBean>) {
        cateBeanList.clear()
        val cateBean = CateBean(
            "", "全部", arrayListOf(), "", 0, "", "", "", "", 0, 0, "", "", true
        )
        cateBeanList.add(cateBean)
        cateBeanList.addAll(mData.data!!.cate)
        tabAdapter.notifyDataSetChanged()
    }

    /*
    * 首页banner数据
    * */
    override fun getBannerList(mData: BaseNetModel<BannerBean>) {
        val listPath = mutableListOf<String>()
        for (slide in mData.data!!.slide) {
            listPath.add(slide.img_url)
        }
        binding.banner.setImageLoader(GlideImageLoader())
        binding.banner.setImages(listPath)
        binding.banner.setDelayTime(2000)
        binding.banner.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, 20f)
            }
        }
        binding.banner.clipToOutline = true
        binding.banner.isAutoPlay(true)
        binding.banner.setIndicatorGravity(BannerConfig.CENTER)
        binding.banner.setOnBannerListener { position ->
            Logger.d(position)
        }
        binding.banner.start()
    }

    /*
    * 首页商品数据
    * */
    @SuppressLint("NotifyDataSetChanged")
    override fun getProdHomeData(mData: BaseNetModel<ProdBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.prods.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                goodsList.clear()
                goodsList.addAll(mData.data!!.prods)
                goodsAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.prods.isEmpty()) {
                page--
            } else {
                goodsList.addAll(mData.data!!.prods)
                goodsAdapter.notifyDataSetChanged()
            }
        }
    }

    /*
    * 签到
    * */
    override fun sign(mData: BaseNetModel<Any>) {
        ToastUtil.showToast(mData.msg)
    }

    override fun noNetworkView() {
        loadService.showCallback(NetWorkErrorCallBack::class.java)
    }

    override fun showError() {
        loadService.showCallback(ErrorCallback::class.java)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 -> {// 签到
                mPresenter!!.sign()
            }
            else -> {
                val bundle = Bundle()
                bundle.putInt(Constants.CATEGORY_TYPE, CategoryType.NEW.ordinal)
                bundle.putString(Constants.CATEGORY_NAME, texts[p2])
                IntentUtil.get()!!.goActivity(activity, CategoryListActivity::class.java, bundle)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onClick(p0: View?) {
        val bundle = Bundle()
        when (p0!!.id) {
            R.id.iv_scan -> {
                RxPermissions(this)
                    .request(permission.WRITE_EXTERNAL_STORAGE, permission.CAMERA)
                    .subscribe { granted: Boolean ->
                        if (granted) { // 用户已经同意该权限
                            register.launch(Intent(activity, CaptureActivity::class.java))
                        } else {
                            Toast.makeText(
                                activity,
                                "拒绝",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
            R.id.ll_new -> {
                bundle.putInt(Constants.CATEGORY_TYPE, CategoryType.NEW.ordinal)
                IntentUtil.get()!!.goActivity(activity, CategoryListActivity::class.java, bundle)
            }
            R.id.ll_large -> {
                bundle.putInt(
                    Constants.CATEGORY_TYPE,
                    CategoryType.LARGE_GOODS.ordinal
                )
                IntentUtil.get()!!.goActivity(activity, CategoryListActivity::class.java, bundle)
            }
            R.id.ll_center -> {
                IntentUtil.get()!!.goActivity(activity, OperationsCenterActivity::class.java)
            }
            R.id.ll_spike_buy -> {
                bundle.putInt(
                    Constants.CATEGORY_TYPE,
                    CategoryType.SPIKE.ordinal
                )
                IntentUtil.get()!!.goActivity(activity, CategoryListActivity::class.java, bundle)
            }
        }
    }
}