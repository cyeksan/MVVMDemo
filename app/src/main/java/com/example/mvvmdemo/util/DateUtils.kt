package com.example.mvvmdemo.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private val dateFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH)
fun Date.dateToString(): String? {
    return try {
        dateFormat.format(this)

    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun String.stringToCalendar() : Calendar? {
    return try {
        val date = dateFormat.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        calendar

    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

