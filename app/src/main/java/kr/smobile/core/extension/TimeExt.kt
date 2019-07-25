package kr.smobile.core.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.getTimeStr(format: String) : String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(this)
}

fun Date.toCalendar() : Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}


fun Date.elpasedTimeSec() : Int {
    val diff = (System.currentTimeMillis() - this.time) / 1000
    return diff.toInt()
}

fun Date.compareToday() : Int {
    val DAY_UNIT = 1000 * 60 * 60 * 24
    val today = System.currentTimeMillis() / DAY_UNIT
    val day = this.time / DAY_UNIT
    return day.compareTo(today)
}


fun Calendar.timeClear() : Calendar {
    set(Calendar.MILLISECOND,0)
    set(Calendar.SECOND,0)
    set(Calendar.MINUTE,0)
    set(Calendar.HOUR_OF_DAY,0)
    return this
}

fun Calendar.getTimeStr(format: String) : String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(this.time)
}

fun Calendar.isToday() : Boolean {
    val today = Calendar.getInstance()
    today.timeClear()
    val temp = this.clone() as Calendar
    temp.timeClear()
    val DAY_UNIT = 1000 * 60 * 60 * 24
    return temp.timeInMillis / DAY_UNIT == today.timeInMillis / DAY_UNIT
}

fun Calendar.isTomorrow() : Boolean {
    val tomorrow = this.clone() as Calendar
    tomorrow.add(Calendar.DAY_OF_YEAR, -1)
    return tomorrow.isToday()
}

fun Calendar.beforeDay(before: Int) : Calendar {
    this.add(Calendar.DAY_OF_YEAR,-before)
    return this
}