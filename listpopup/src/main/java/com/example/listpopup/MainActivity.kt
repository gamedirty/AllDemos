package com.example.listpopup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            showPop(it)
        }
    }

    private fun showPop(view: View) {
        ListMenuPopup().with(this).anchor(view).setData(listOf("我是哈哈", "你是哈哈", "哈哈哈哈哈")).show()
    }


}