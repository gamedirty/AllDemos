package com.example.listpopup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView

class ListMenuPopup {
    private lateinit var mContext: Context
    private lateinit var mAnchor: View
    private lateinit var mData: List<String>
    private var mSelectable: Boolean = false
    private val innerPopup by lazy {
        PopupMenu(mContext, mAnchor).apply {
            mData.forEach {
                menu.add(it).apply {
                    val contentView: TextView =
                        LayoutInflater.from(mContext)
                            .inflate(R.layout.layout_menu_action, null) as TextView
                    contentView.text = it
                }
            }
            setOnMenuItemClickListener {
                it.isChecked = true
                return@setOnMenuItemClickListener true
            }
        }
    }

    fun dismiss() {
        innerPopup.dismiss()
    }

    fun with(context: Context): ListMenuPopup {
        mContext = context
        return this
    }

    fun anchor(view: View): ListMenuPopup {
        mAnchor = view
        return this
    }

    fun setData(mData: List<String>): ListMenuPopup {
        this.mData = mData
        return this
    }

    fun selectable(selectable: Boolean): ListMenuPopup {
        mSelectable = selectable
        return this
    }

    fun show() {
        innerPopup.show()
    }

}