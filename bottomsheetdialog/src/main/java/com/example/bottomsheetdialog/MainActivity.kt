package com.example.bottomsheetdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = findViewById<View>(R.id.nestList)

        val behav = BottomSheetBehavior.from(view)

        behav.apply {
            peekHeight = 300
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        view.postDelayed({

            behav.state = BottomSheetBehavior.STATE_COLLAPSED
            behav.isHideable = false
        }, 1000)

    }
}