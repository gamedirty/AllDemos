package com.example.shadow

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.graphics.RectF
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import com.google.android.material.shape.MaterialShapeDrawable

fun View.addShadow(
    @ColorInt shadowColor: Int = Color.BLACK,
    elevation: Float = 40f,
    cornerSize: (RectF) -> Float = HALF_HEIGHT_CORNER,
    alpha: Float = .3f
) {
    this.background = ShadowDrawable(alpha).apply {
        this.setCornerSize(cornerSize)
        this.fillColor = ColorStateList.valueOf(Color.WHITE)
        this.setShadowColor(
            shadowColor
        )
        this.setUseTintColorForShadow(true)
    }
    this.elevation = elevation
}


val HALF_WIDTH_CORNER: (RectF) -> Float = {
    it.width() / 2
}

val HALF_HEIGHT_CORNER: (RectF) -> Float = {
    it.width() / 2
}

class ShadowDrawable(private val alpha: Float) : MaterialShapeDrawable() {
    override fun getOutline(outline: Outline) {
        super.getOutline(outline)
        outline.alpha = alpha
    }
}