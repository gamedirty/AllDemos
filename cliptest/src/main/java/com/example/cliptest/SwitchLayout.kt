package com.example.cliptest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class SwitchLayout : ViewGroup {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val onColor = Color.RED
    private val offColor = Color.GREEN

    init {
        setWillNotDraw(false)
    }

    private val diffWidth by lazy { measuredHeight / 4 }

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    /**
     * 子view各自居中
     */

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        if (canvas == null) return
//        canvas.drawColor(offColor)
//        paint.color = onColor
//        canvas.drawRect(0f, 0f, (viewWidth / 2).toFloat(), viewHeight.toFloat(), paint)
        canvas?.drawColor(Color.GREEN)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        var maxWidth = 0
        children.forEach {
            maxWidth = Math.max(maxWidth, it.measuredWidth)
        }
        val childeW = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY)
        children.forEach {
            if (it.visibility != GONE)
                it.measure(childeW, heightMeasureSpec)
        }
        setMeasuredDimension(maxWidth * childCount - (childCount - 1) * diffWidth, measuredHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        children.forEachIndexed { index, view ->
            view.layout(
                index * (view.measuredWidth - diffWidth),
                t,
                index * (view.measuredWidth - diffWidth) + view.measuredWidth,
                b
            )
        }
    }
}