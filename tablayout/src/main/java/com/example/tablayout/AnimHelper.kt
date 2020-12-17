package com.example.tablayout

import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout


object AnimHelper {


    fun setupAnim(
        collapsView: AppBarLayout,
        littleText: TextView,
        bitText: TextView,
        searchView: TextView,
        littleSearch: View, maxHeight: Int = 0
    ) {


        collapsView.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.i(
                "zhjh",
                "verticalOffset：$verticalOffset,百分比是：${verticalOffset * 1.0f / maxHeight}"
            )
        })


    }

}