package com.app.shop.view.pop

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.app.shop.R
import com.app.shop.adapter.SpecAdapter
import com.app.shop.bean.CreateOrderBean
import com.app.shop.bean.ProdInfo
import com.app.shop.bean.SkuInfosBean
import com.app.shop.bean.event.CateEvent
import com.app.shop.ui.activity.ConfirmOrderActivity
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
import com.app.shop.view.dialog.PrivacyDialog
import com.bumptech.glide.Glide
import com.lxj.xpopup.core.BottomPopupView
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.w3c.dom.Text

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
class SpecificationPop(context: Context, type: Int, prodInfo: ProdInfo) : BottomPopupView(context) {
    lateinit var tvBuy: TextView
    lateinit var ivGoods: ImageView
    lateinit var mRecyclerView: RecyclerView
    lateinit var tvPrice: TextView
    lateinit var tvStocks: TextView
    lateinit var tvReduce: TextView
    lateinit var tvAdd: TextView
    lateinit var etNum: EditText

    private var prodInfo: ProdInfo

    private var type: Int = 0

    var binding: ViewBinding? = null
    lateinit var specAdapter: SpecAdapter

    private var sku_id = ""
    private var stocksCount: Int = 0
    private var attr: String = ""

    private var sku_price = "0";
    private var sku_point = "0";

    init {
        this.prodInfo = prodInfo
        this.type = type
    }

    override fun getImplLayoutId(): Int {
        return R.layout.pop_specification
    }

    override fun onCreate() {
        super.onCreate()

        initView()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        if(type==0){
            tvBuy.text = "加入购物车"
        }else{
            tvBuy.text = "立即购买"
        }

        Glide.with(context).load(prodInfo.pic).error(R.drawable.icon_default_pic)
            .placeholder(R.drawable.icon_default_pic).into(ivGoods)

        for (item in prodInfo.sku_info) {
            for (item1 in item.sku_prop) {
                item1.isCheck = false
            }
        }
        for (item in prodInfo.sku_info) {
            item.sku_prop[0].isCheck = true
        }

        mRecyclerView = findViewById(R.id.mRecyclerView)
        specAdapter = SpecAdapter(context, prodInfo.sku_info)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = specAdapter

        specAdapter.setOnItemClickListener(object : SpecAdapter.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int, position1: Int) {
                for (item in prodInfo.sku_info[position].sku_prop) {
                    item.isCheck = false
                }
                prodInfo.sku_info[position].sku_prop[position1].isCheck = true
                specAdapter.notifyDataSetChanged()
                updateHeadUi()
            }
        })

        updateHeadUi()

        tvReduce.setOnClickListener {
            if (etNum.text.toString().toInt() > 1) {
                etNum.setText((etNum.text.toString().toInt() - 1).toString())
            } else {
                ToastUtil.showToast("数量至少为1~")
            }
        }

        tvAdd.setOnClickListener {
            if (etNum.text.toString().toInt() < stocksCount) {
                etNum.setText((etNum.text.toString().toInt() + 1).toString())
            } else {
                ToastUtil.showToast("不能在加啦~")
            }
        }

        etNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (etNum.text.toString().isNotEmpty() || etNum.text.toString().isNotBlank()) {
                    if (etNum.text.toString().toInt() < 1) {
                        etNum.setText("1")
                    }
                    if (etNum.text.toString().toInt() > stocksCount) {
                        etNum.setText(stocksCount.toString())
                    }
                }
            }
        })

        tvBuy.setOnClickListener {
            if (type == 0) {// 加入购物车
                onClickListener!!.addCartClick(
                    etNum.text.toString().toInt(),
                    prodInfo.prod_id,
                    sku_id
                )
            } else {// 生成订单页
                var isAppointment = false
                if (prodInfo.tags.contains("预约")) {
                    isAppointment = true
                }
                val createOrderBean = CreateOrderBean()
                createOrderBean.pic = prodInfo.pic
                createOrderBean.count = etNum.text.toString().toInt()
                createOrderBean.attr = attr
                createOrderBean.ori_point = sku_point
                createOrderBean.price = sku_price
                createOrderBean.delivery_cost = prodInfo.delivery_cost
                createOrderBean.service_cost = prodInfo.service_cost
                createOrderBean.prod_id = prodInfo.prod_id
                createOrderBean.prod_name = prodInfo.prod_name
                createOrderBean.shop_id = prodInfo.shop_id
                createOrderBean.sku_id = sku_id
                createOrderBean.isAppointment = isAppointment
                IntentUtil.get()!!
                    .goActivity(context, ConfirmOrderActivity::class.java, createOrderBean)
            }
            dismiss()
        }
    }

    private fun initView() {
        tvBuy = findViewById(R.id.tv_buy)
        ivGoods = findViewById(R.id.iv_goods)
        tvPrice = findViewById(R.id.tv_price)
        tvStocks = findViewById(R.id.tv_stocks)
        tvReduce = findViewById(R.id.tv_reduce)
        tvAdd = findViewById(R.id.tv_add)
        etNum = findViewById(R.id.et_num)
    }

    private fun updateHeadUi() {
        var name = ""
        var queryAttr = ""
        for (item in prodInfo.sku_info) {
            for (item1 in item.sku_prop) {
                if (item1.isCheck) {
                    name += item1.name
                    queryAttr = queryAttr + item.sku + ":" + item1.name + ","
                }
            }
        }
        attr = name
        Logger.d(attr)
        Logger.d(queryAttr)
        if (queryAttr.isNotEmpty()) {
            queryAttr = queryAttr.substring(0, queryAttr.length - 1)
        }
        onClickListener!!.queryAttr(queryAttr)

        for (skuCountItem in prodInfo.sku_count) {
            if (name == skuCountItem.sku_name) {
                /* tvPrice.text = String.format(
                     context.getString(R.string.price),
                     skuCountItem.price
                 )
                 tvStocks.text = String.format(
                     context.getString(R.string.stocks),
                     skuCountItem.stocks
                 )*/
                sku_id = skuCountItem.sku_id
                stocksCount = skuCountItem.stocks
            }
        }
    }

    var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener?): SpecificationPop? {
        this.onClickListener = onClickListener
        return this
    }

    interface OnClickListener {
        fun addCartClick(count: Int, gid: String, sku: String)
        fun queryAttr(props: String)
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getAttrData(skuInfosBean: SkuInfosBean) {
        Logger.d(skuInfosBean)
        tvStocks.text = String.format(
            context.getString(R.string.stocks),
            skuInfosBean.stocks
        )
        tvPrice.text = String.format(
            context.getString(if (skuInfosBean.point.toDouble() > 0) R.string.goods_integral else R.string.price),
            if (skuInfosBean.point.toDouble() > 0) skuInfosBean.point else skuInfosBean.price
        )
        sku_price = skuInfosBean.price
        sku_point = skuInfosBean.point
        sku_id=skuInfosBean.sku_id
        Logger.d(skuInfosBean)
    }
}

