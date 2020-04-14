package com.cortado.mafkir.model.time

enum class IntervalType(val text: String) {
    WEEK("weeks"),
    DAY("days");

    companion object {
        @JvmStatic
        fun fromText(text: String): IntervalType {
            return values().first { it.text == text }
        }
    }
}