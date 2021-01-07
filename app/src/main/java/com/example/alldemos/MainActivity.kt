package com.example.alldemos

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.addShadow
import com.example.alldemos.shadow.ShadowProperty
import com.example.alldemos.shadow.ShadowViewDrawable
import com.example.dp
import com.example.zhjhLog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val valueAnimator: ValueAnimator by lazy {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1200
            repeatCount = 1
            addUpdateListener {
                Log.i("zhjh", "update:${it.animatedValue as Float}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        valueAnimator.start()
        lookUp()
        list.addShadow {
            radius = 10f
            shadowColor = Color.YELLOW
            offsetX = 15f
            offsetY = 15f
        }


        val sp = ShadowProperty().apply {
            shadowColor = Color.YELLOW
            shadowDx = 30
            shadowDy = 30
            shadowRadius = 20
            shadowSide = ShadowProperty.LEFT or ShadowProperty.RIGHT or ShadowProperty.BOTTOM
        }

        ViewCompat.setBackground(panel, ShadowViewDrawable(sp, Color.BLACK, 0f, 0f))

        panel.setLayerType(View.LAYER_TYPE_SOFTWARE, null)




        seekbar.max = 25.dp(this)

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                panel.translationZ = progress.dp(this@MainActivity).toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        seekbar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                panel.elevation = progress.dp(this@MainActivity).toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun lookUp() {
        BottomSheetBehavior.from(list).apply {
            halfExpandedRatio = .3f
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    "onSlide:$slideOffset".zhjhLog()
                }

            })
        }

    }
}