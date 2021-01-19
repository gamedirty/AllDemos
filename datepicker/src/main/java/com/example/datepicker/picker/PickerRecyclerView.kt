package com.example.datepicker.picker

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

open class PickerRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var mOrientation = PickerLayoutManager.VERTICAL
    var mVisibleCount = 3
    var mIsLoop = false
    var mScaleX = 1.0f
    var mScaleY = 1.0f
    var mAlpha = 1.0f

    var mDividerVisible = true
    var mDividerSize = 1.0f
    var mDividerColor = Color.LTGRAY
    var mDividerMargin = 0f

    init {
        setAttrs()
        resetLayoutManager()
    }

    private fun setAttrs() {
        removeDivider()
        if (mDividerVisible) {
            setDivider()
        }
    }

    fun resetLayoutManager(
        orientation: Int = mOrientation,
        visibleCount: Int = mVisibleCount,
        isLoop: Boolean = mIsLoop,
        scaleX: Float = mScaleX,
        scaleY: Float = mScaleY,
        alpha: Float = mAlpha
    ) {
        val lm = PickerLayoutManager(
            orientation,
            visibleCount,
            isLoop,
            scaleX,
            scaleY,
            alpha
        )
        resetLayoutManager(lm)
    }

    fun resetLayoutManager(lm: PickerLayoutManager) {
        layoutManager = lm
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        if (layout !is PickerLayoutManager) {
            throw IllegalArgumentException("LayoutManager only can use PickerLayoutManager")
        }
        setAttrs()
    }

    fun getSelectedPosition() = layoutManager.getSelectedPosition()

    override fun getLayoutManager(): PickerLayoutManager {
        return super.getLayoutManager() as PickerLayoutManager
    }

    //重新设置属性值

    fun setOrientation(orientation: Int) {
        this.mOrientation = orientation
    }

    fun setVisibleCount(count: Int) {
        this.mVisibleCount = count
    }

    fun setIsLoop(isLoop: Boolean) {
        this.mIsLoop = isLoop
    }

    fun setItemScaleX(scaleX: Float) {
        this.mScaleX = scaleX
    }

    fun setItemScaleY(scaleY: Float) {
        this.mScaleY = scaleY
    }

    fun setItemAlpha(alpha: Float) {
        this.mAlpha = alpha
    }

    fun setDividerVisible(visible: Boolean) {
        this.mDividerVisible = visible
    }

    fun setDividerSize(@Px size: Float) {
        this.mDividerSize = size
    }

    fun setDividerColor(@ColorInt color: Int) {
        this.mDividerColor = color
    }

    fun setDividerMargin(margin: Float) {
        this.mDividerMargin = margin
    }

    //设置分割线

    private fun setDivider() {
        this.addItemDecoration(
            PickerItemDecoration(
                mDividerColor,
                mDividerSize,
                mDividerMargin
            )
        )
    }

    private fun removeDivider() {
        val count = this.itemDecorationCount
        for (index in 0 until count) {
            this.removeItemDecorationAt(index)
        }
    }

    /**
     *
     */

    fun addOnSelectedItemListener(listener: OnItemSelectedListener) {
        layoutManager.addOnItemSelectedListener(listener)
    }

    fun removeOnItemSelectedListener(listener: OnItemSelectedListener) {
        layoutManager.removeOnItemSelectedListener(listener)
    }

    /**
     *
     */
    fun addOnItemFillListener(listener: PickerLayoutManager.OnItemFillListener) {
        layoutManager.addOnItemFillListener(listener)
    }

    fun removeOnItemFillListener(listener: PickerLayoutManager.OnItemFillListener) {
        layoutManager.removeOnItemFillListener(listener)
    }

}