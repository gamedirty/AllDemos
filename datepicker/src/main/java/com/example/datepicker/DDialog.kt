package com.example.datepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout


class DDialog : BottomSheetDialogFragment() {
    private var mBottomSheetBehavior: BottomSheetBehavior<View>? = null

    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBottomSheetBehavior?.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }

        override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_select_date, null)
    }

    override fun onStart() {
        super.onStart()
        view?.post {
            val parent = view!!.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            mBottomSheetBehavior = behavior as BottomSheetBehavior<View>?
            mBottomSheetBehavior?.addBottomSheetCallback(mBottomSheetBehaviorCallback)
        }

        val dateTimeTab = view?.findViewById<TabLayout>(R.id.dateTimeTab)
        dateTimeTab?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
}