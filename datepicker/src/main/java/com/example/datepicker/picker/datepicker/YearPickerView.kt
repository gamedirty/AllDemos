package com.example.datepicker.picker.datepicker

import android.content.Context
import android.util.AttributeSet
import com.example.datepicker.picker.PickerUtils
import com.example.datepicker.picker.TextPickerView

class YearPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextPickerView(context, attrs, defStyleAttr) {

    fun setYearInterval(
        startYear: Int = PickerUtils.START_YEAR,
        endYear: Int = PickerUtils.getEndYear()
    ) {
        mItems.clear()
        for (year in startYear..endYear) {
            mItems.add(year.toString())
        }
        adapter?.notifyDataSetChanged()
    }

    fun setYear(){

    }

    fun getYear(position: Int): Int {
        return mItems[position].toInt()
    }

    fun getYear(): Int {
        return getYearStr().toInt()
    }

    fun getYearStr(): String {
        return getSelectedItem()
    }

    override fun getTail(): String {
        return "年"
    }
}