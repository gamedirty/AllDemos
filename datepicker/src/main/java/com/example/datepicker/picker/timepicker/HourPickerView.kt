package com.example.datepicker.picker.timepicker

import android.content.Context
import android.util.AttributeSet
import com.example.datepicker.picker.PickerUtils
import com.example.datepicker.picker.TextPickerView

class HourPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextPickerView(context, attrs, defStyleAttr) {

    init {
        setHourInterval()
    }

    @SuppressWarnings
    fun setHourInterval(
        start: Int = PickerUtils.START_HOUR,
        end: Int = PickerUtils.END_HOUR
    ) {
        mItems.clear()
        for (hour in start..end) {
            mItems.add(PickerUtils.formatTwoChars(hour))
        }
        adapter?.notifyDataSetChanged()
    }

    fun getHourStr() = getSelectedItem()

    fun getHour() = getHourStr().toInt()

    override fun getTail(): String {
        return "时"
    }
}