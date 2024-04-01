package vn.geekup.app.utils

import java.text.SimpleDateFormat
import java.time.ZoneOffset.UTC
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val PATTEN_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS"
private const val PATTEN_DATE_DAY_NAME = "EEEE"
private const val PATTEN_MOMENT_DATE = "dd, MMM yyyy"

fun String.toDateTimeZone(): Date {
    val dateFormat = SimpleDateFormat(PATTEN_DATE_TIME, Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone(UTC)
    val pasTimeResult = dateFormat.parse(this) ?: Date()
    dateFormat.timeZone = TimeZone.getDefault()
    val pasTime = dateFormat.format(pasTimeResult)
    return dateFormat.parse(pasTime) ?: Date()
}
