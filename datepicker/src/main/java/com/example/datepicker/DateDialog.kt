package com.example.datepicker

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

open class DateDialog(context: Context?) :
    AppCompatDialog(context, R.style.Theme_Design_Light_BottomSheetDialog) {

    private var behavior: BottomSheetBehavior<FrameLayout>? = null

    private var container: FrameLayout? = null

    var dismissWithAnimation = false

    var cancelable1 = true
    private var canceledOnTouchOutside = true
    private var canceledOnTouchOutsideSet = false

    init {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun setContentView(@LayoutRes layoutResId: Int) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        if (window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun setContentView(view: View) {
        super.setContentView(wrapInBottomSheet(0, view, null)!!)
    }


    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        super.setContentView(wrapInBottomSheet(0, view, params)!!)
    }

    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(cancelable)
        if (this.cancelable1 != cancelable) {
            this.cancelable1 = cancelable
            if (behavior != null) {
                behavior!!.isHideable = cancelable
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (behavior != null && behavior!!.state == BottomSheetBehavior.STATE_HIDDEN) {
            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    /**
     * This function can be called from a few different use cases, including Swiping the dialog down
     * or calling `dismiss()` from a `BottomSheetDialogFragment`, tapping outside a dialog, etc...
     *
     *
     * The default animation to dismiss this dialog is a fade-out transition through a
     * windowAnimation. Call [.setDismissWithAnimation] if you want to utilize the
     * BottomSheet animation instead.
     *
     *
     * If this function is called from a swipe down interaction, or dismissWithAnimation is false,
     * then keep the default behavior.
     *
     *
     * Else, since this is a terminal event which will finish this dialog, we override the attached
     * [BottomSheetBehavior.BottomSheetCallback] to call this function, after [ ][BottomSheetBehavior.STATE_HIDDEN] is set. This will enforce the swipe down animation before
     * canceling this dialog.
     */
    override fun cancel() {
        val behavior = getBehavior()
        if (!dismissWithAnimation || behavior.state == BottomSheetBehavior.STATE_HIDDEN) {
            super.cancel()
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    override fun setCanceledOnTouchOutside(cancel: Boolean) {
        super.setCanceledOnTouchOutside(cancel)
        if (cancel && !cancelable1) {
            cancelable1 = true
        }
        canceledOnTouchOutside = cancel
        canceledOnTouchOutsideSet = true
    }

    fun getBehavior(): BottomSheetBehavior<FrameLayout> {
        if (behavior == null) {
            // The content hasn't been set, so the behavior doesn't exist yet. Let's create it.
            ensureContainerAndBehavior()
        }
        return behavior!!
    }

    /** Creates the container layout which must exist to find the behavior  */
    private fun ensureContainerAndBehavior(): FrameLayout? {
        if (container == null) {
            container =
                View.inflate(context, R.layout.design_bottom_sheet_dialog, null) as FrameLayout
            val bottomSheet =
                container!!.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            behavior = BottomSheetBehavior.from(bottomSheet)
            behavior!!.addBottomSheetCallback(bottomSheetCallback)
            behavior!!.isHideable = cancelable1
        }
        return container
    }

    private fun wrapInBottomSheet(
        layoutResId: Int, view: View?, params: ViewGroup.LayoutParams?
    ): View? {
        var view = view
        ensureContainerAndBehavior()
        val coordinator = container!!.findViewById<View>(R.id.coordinator) as CoordinatorLayout
        if (layoutResId != 0 && view == null) {
            view = layoutInflater.inflate(layoutResId, coordinator, false)
        }
        val bottomSheet = container!!.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        bottomSheet.removeAllViews()
        if (params == null) {
            bottomSheet.addView(view)
        } else {
            bottomSheet.addView(view, params)
        }
        // We treat the CoordinatorLayout as outside the dialog though it is technically inside
        coordinator
            .findViewById<View>(R.id.touch_outside)
            .setOnClickListener {
                if (cancelable1 && isShowing && shouldWindowCloseOnTouchOutside()) {
                    cancel()
                }
            }
        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(
            bottomSheet,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View, info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    if (cancelable1) {
                        info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS)
                        info.isDismissable = true
                    } else {
                        info.isDismissable = false
                    }
                }

                override fun performAccessibilityAction(
                    host: View,
                    action: Int,
                    args: Bundle
                ): Boolean {
                    if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && cancelable1) {
                        cancel()
                        return true
                    }
                    return super.performAccessibilityAction(host, action, args)
                }
            })
        bottomSheet.setOnTouchListener { _, _ -> // Consume the event and prevent it from falling through
            true
        }
        return container
    }

    private fun shouldWindowCloseOnTouchOutside(): Boolean {
        if (!canceledOnTouchOutsideSet) {
            val a = context
                .obtainStyledAttributes(intArrayOf(android.R.attr.windowCloseOnTouchOutside))
            canceledOnTouchOutside = a.getBoolean(0, true)
            a.recycle()
            canceledOnTouchOutsideSet = true
        }
        return canceledOnTouchOutside
    }

    fun removeDefaultCallback() {
        behavior!!.removeBottomSheetCallback(bottomSheetCallback)
    }

    private val bottomSheetCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(
            bottomSheet: View, @BottomSheetBehavior.State newState: Int
        ) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                cancel()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }
}