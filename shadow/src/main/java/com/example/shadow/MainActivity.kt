package com.example.shadow

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv1).apply {

//            addShadow(cornerSize = HALF_HEIGHT_CORNER)
            background =
                MaterialShapeDrawable.createWithElevationOverlay(this@MainActivity).apply {
                    setCornerSize {
                        it.height() / 2
                    }
                }
            translationZ = 10f
        }

        findViewById<View>(R.id.tv2).apply {
            addShadow(shadowColor = Color.RED, elevation = 30f)
        }
    }
}