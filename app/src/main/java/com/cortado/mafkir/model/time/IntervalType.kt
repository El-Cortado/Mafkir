package com.cortado.mafkir.model.time

enum class IntervalType(val text: String, val millisInUnit: Long) {
    WEEK("weeks", 604800000),
    DAY("days", 86400000);

    companion object {
        @JvmStatic
        fun fromText(text: String): IntervalType {
            return values().first { it.text == text }
        }
    }
}