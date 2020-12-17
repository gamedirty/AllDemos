package com.example

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.Nullable


fun String.zhjhLog() {

    Log.i("zhjh", "内容：$this")
    this.apply { }
    with("") {

    }

}

open class ShadowOptions {
    var radius = 1f
    var shadowColor = Color.TRANSPARENT
    var offsetX = 0f
    var offsetY = 0f
}

/**
 * 阴影工具类 试试[ViewOutlineProvider]和[ShadowDrawable]
 */
inline fun View.addShadow(block: ShadowOptions.() -> Unit) {
    val options = ShadowOptions()

    block.invoke(options)

    this.background = ShadowDrawable(intArrayOf(Color.WHITE), options.radius, options.shadowColor, options.radius, options.offsetX, options.offsetY)
}


var View.shadow:ShadowOptions
    get() {
        return this.shadow
    }
    set(value) {

    }


class ShadowDrawable(// 背景形状
        bgColor: IntArray = intArrayOf(Color.WHITE), shapeRadius: Float = 0f, shadowColor: Int = 0, shadowRadius: Float = 0f, offsetX: Float = 0f, offsetY: Float = 0f) : Drawable() {
    private val mPaint: Paint
    private val mShadowRadius // 阴影圆角
            : Float
    private val mShapeRadius // 背景圆角
            : Float
    private val mOffsetX // 阴影的水平偏移量
            : Float
    private val mOffsetY // 阴影的垂直偏移量
            : Float
    private val mBgColor: IntArray? // 背景颜色
    private lateinit var mRect: RectF
    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        mRect = RectF((left + mShadowRadius - mOffsetX), (top + mShadowRadius - mOffsetY), (right - mShadowRadius - mOffsetX),
                (bottom - mShadowRadius - mOffsetY))
    }

    override fun draw(canvas: Canvas) {
            canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mPaint)
            val newPaint = Paint()
            if (mBgColor != null) {
                if (mBgColor.size == 1) {
                    newPaint.color = mBgColor[0]
                } else {
                    newPaint.shader = LinearGradient(mRect.left, mRect.height() / 2, mRect.right, mRect.height() / 2, mBgColor,
                            null, Shader.TileMode.CLAMP)
                }
            }
            newPaint.isAntiAlias = true
            canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, newPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(@Nullable colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    init {
        mBgColor = bgColor
        mShapeRadius = shapeRadius
        mShadowRadius = shadowRadius
        mOffsetX = offsetX
        mOffsetY = offsetY
        mPaint = Paint().apply {
            color = Color.TRANSPARENT
            isAntiAlias = true
            setShadowLayer(shadowRadius, offsetX, offsetY, shadowColor)
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
        }
    }
}


fun Int.dp(context: Context):Int{
   return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}
object DpTools {
    fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics()).toInt()
    }

    fun dp2pxF(context: Context, dpValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics())
    }
}