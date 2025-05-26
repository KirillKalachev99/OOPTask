package ru.netology

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateMethod {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun getCurrentDate(): String{
        return LocalDate.now().format(formatter)
    }
}