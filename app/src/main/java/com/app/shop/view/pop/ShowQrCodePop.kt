package com.app.shop.view.pop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.app.shop.R
import com.app.shop.manager.AccountManager
import com.app.shop.util.BitmapUtil
import com.app.shop.util.ToastUtil
import com.lxj.xpopup.core.CenterPopupView
import com.uuzuche.lib_zxing.activity.CodeUtils

/**
 * @author chenshichun
 * 创建日期：2022/8/24
 * 描述：
 *
 */
class ShowQrCodePop(context: Context) : CenterPopupView(context) {
    private lateinit var ivQrCode: ImageView
    private lateinit var bitmap: Bitmap

    override fun getImplLayoutId(): Int {
        return R.layout.pop_show_qr_code
    }

    override fun onCreate() {
        super.onCreate()
        ivQrCode = findViewById<ImageView>(R.id.iv_qr_code)
        bitmap = CodeUtils.createImage(
            AccountManager.getAccountInfo()!!.inv_code,
            200,
            200,
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round, null)
        )
        ivQrCode.setImageBitmap(
            bitmap
        )

        ivQrCode.setOnLongClickListener {
            BitmapUtil.saveImageToGallery(context, bitmap, BitmapUtil.SaveGalleryListener {
                ToastUtil.showToast("二维码保存成功")
            })
            false
        }
    }
}