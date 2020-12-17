package com.example.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return 2
            }

            override fun getItem(position: Int): Fragment {
                return SimpleFragment()
            }

        }

        AnimHelper.setupAnim(
            appBarLayout,
            littleText = littleTitle,
            bigTitle,
            searchView,
            littleSearch, maxHeight = 74.dp
        )
    }

    val Int.dp: Int
        get() {
            val scale = resources?.displayMetrics?.density
            return (this * scale!! + .5f).toInt()
        }


}