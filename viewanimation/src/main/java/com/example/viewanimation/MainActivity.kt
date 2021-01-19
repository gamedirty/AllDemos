package com.example.viewanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.hahah).setOnClickListener {
            
            it.animate().alpha(1f)
                .withEndAction {

                }
                .start()
            
//            it.animate().alpha(0f).setDuration(280)
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator?) {
//                        super.onAnimationEnd(animation)
//                        it.visibility = View.GONE
//                    }
//                })
//                .start()
        }
    }
}