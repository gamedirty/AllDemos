package com.example.cliptest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CustomView : View {
    constructor(context: Context?) : super(context)

    val paint = Paint().apply {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        paint.setColor(Color.RED)
        canvas.drawRect(0f, 0f, 200f, 200f, paint)
        canvas?.save()
        paint.color = Color.GREEN
        canvas?.clipRect(100, 100, 300, 300)
        canvas?.drawRect(0f, 100f, 300f, 300f, paint)

        canvas?.drawRect(0f, 100f, 300f, 300f, paint)
        canvas?.restore()
    }
}