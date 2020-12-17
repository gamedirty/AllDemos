package com.example.shadow

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.RectF
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.shape.MaterialShapeDrawable

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("RestrictedApi")
fun View.addShadow(
    shadowColor: Int = Color.BLACK,
    elevation: Float = 40f,
    cornerSize: (RectF) -> Float = HALF_HEIGHT_CORNER,
) {
    this.background = MaterialShapeDrawable().apply {
        this.setCornerSize(cornerSize)
        this.fillColor = ColorStateList.valueOf(Color.WHITE)
        this.shadowVerticalOffset = 300
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