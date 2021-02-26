package me.simple.picker.timepicker

import android.content.Context
import android.util.AttributeSet
import com.example.datepicker.picker.PickerUtils
import com.example.datepicker.picker.TextPickerView

class SecondPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextPickerView(context, attrs, defStyleAttr) {

    init {
        setMinuteInterval()
    }

    fun setMinuteInterval(start: Int = 0, end: Int = 60) {
        mItems.clear()
        for (minute in start until end) {
            mItems.add(PickerUtils.formatTwoChars(minute))
        }
        adapter?.notifyDataSetChanged()
    }

    fun getSecond() = getSelectedItem()

    override fun getTail(): String {
        return "秒"
    }
}