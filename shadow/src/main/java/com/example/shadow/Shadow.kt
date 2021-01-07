package com.example.shadow

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.MaterialShapeDrawable


fun View.addShadow(
    @ColorInt shadowColor: Int = Color.parseColor("#001B53"),
    elevation: Float = 12.5f*3,
    cornerSize: (RectF) -> Float = HALF_HEIGHT_CORNER,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = .25f,
) {
    this.background = ShadowDrawable(alpha).apply {
        this.setCornerSize(cornerSize)
        this.setShadowColor(
            shadowColor
        )
        if (this@addShadow.background == null)
            this.fillColor = ColorStateList.valueOf(Color.WHITE)
        this.setUseTintColorForShadow(
            true
        )
    }
    this.elevation = elevation
}

fun View.removeShadow() {
    this.elevation = 0f
}


val HALF_WIDTH_CORNER: (RectF) -> Float = {
    it.width() / 2
}

val HALF_HEIGHT_CORNER: (RectF) -> Float = {
    it.width() / 2
}

class ShadowDrawable(private val alpha: Float) :
    MaterialShapeDrawable() {
    override fun getOutline(outline: Outline) {
        super.getOutline(outline)
        outline.alpha = alpha
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.GREEN)
    }
}