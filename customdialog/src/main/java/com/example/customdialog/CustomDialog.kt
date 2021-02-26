package com.example.customdialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle

class CustomDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_aly)
    }

}