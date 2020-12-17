package com.example.alldemos

import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi

open class Shadow {

   open fun shadow(){
        val outlineProvider = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
            }
        }


    }
}