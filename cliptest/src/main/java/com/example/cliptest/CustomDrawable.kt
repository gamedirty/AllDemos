package com.example.cliptest

import android.graphics.*
import android.graphics.drawable.AnimationDrawable

class CustomDrawable : AnimationDrawable() {

    private val paint = Paint()
    private val rect = Rect()

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.RED)
        canvas.save()
        canvas.clipRect(0, 0, 100, 100)
        canvas.drawColor(Color.GREEN)
        canvas.restore()
    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
        rect.set(bounds)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}