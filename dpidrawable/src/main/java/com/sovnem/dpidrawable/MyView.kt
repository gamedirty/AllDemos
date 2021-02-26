package com.sovnem.dpidrawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.CycleInterpolator
import android.widget.OverScroller
import android.widget.Scroller

class MyView(context: Context?) : View(context) {


    var mLeft = 0f


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (scroller.computeScrollOffset()) {
            mLeft = scroller.currX.toFloat()
            postInvalidate()
        }
        canvas.save()
        canvas.clipRect(mLeft, 0f, mLeft + 200, 200f)
        canvas.drawColor(Color.GREEN)
        canvas.restore()
    }

    init {

        postDelayed({ start() }, 2000)
    }

    private val scroller by lazy { OverScroller(getContext(), AnticipateOvershootInterpolator(2f)) }

    private fun start() {
        scroller.startScroll(0, 0, 800, 0, 5000)
        invalidate();
    }


}