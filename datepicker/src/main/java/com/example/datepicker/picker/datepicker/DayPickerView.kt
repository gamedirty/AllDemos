package com.example.datepicker.picker.datepicker

import android.content.Context
import android.util.AttributeSet
import com.example.datepicker.picker.PickerUtils
import com.example.datepicker.picker.TextPickerView

class DayPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextPickerView(context, attrs, defStyleAttr) {

    init {
        setDayInterval()
    }

    @SuppressWarnings
    fun setDayInterval(
        startDay: Int = 1,
        endDay: Int = 31
    ) {
        mItems.clear()
        for (day in startDay..endDay) {
            mItems.add(PickerUtils.formatTwoChars(day))
        }
        adapter?.notifyDataSetChanged()
    }

    @SuppressWarnings
    fun setDayIntervalByMonth(
        year: Int,
        month: Int,
        startDay: Int = 1
    ) {
        setDayInterval(startDay, endDay = PickerUtils.getDayCountInMonth(year, month))
    }

    fun getDayStr() = getSelectedItem()

    fun getDay() = getDayStr().toInt()

    override fun getTail(): String {
        return "日"
    }
}