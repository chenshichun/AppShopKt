package com.app.shop.view.pop

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.app.shop.R
import com.app.shop.manager.AccountManager
import com.bumptech.glide.Glide
import com.lxj.xpopup.core.CenterPopupView

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
       /* bitmap = CodeUtils.createImage(
            AccountManager.getAccountInfo()!!.inv_code,
            200,
            200,
            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round, null)
        )
        ivQrCode.setImageBitmap(
            bitmap
        )*/

        Glide.with(context).load(AccountManager.getAccountInfo()!!.my_qr).into(ivQrCode)

        ivQrCode.setOnLongClickListener {
            /*BitmapUtil.saveImageToGallery(context, bitmap, BitmapUtil.SaveGalleryListener {
                ToastUtil.showToast("二维码保存成功")
            })*/
            false
        }
    }
}