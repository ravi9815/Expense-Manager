package com.example.easykhatabahi.utils

import java.text.SimpleDateFormat
import java.util.Date

object Helper {
    fun formatDate(date: Date?): String {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
        return dateFormat.format(date)
    }

    fun formatDateByMonth(date: Date?): String {
        val dateFormat = SimpleDateFormat("MMMM, yyyy")
        return dateFormat.format(date)
    }
}
