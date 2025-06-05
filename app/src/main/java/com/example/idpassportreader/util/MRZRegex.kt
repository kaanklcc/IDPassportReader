package com.example.idpassportreader.util

object MRZRegex {
    const val ID_CARD_TD_1_LINE_1_REGEX = "([A|C|I][A-Z0-9<]{1})([A-Z]{3})([A-Z0-9<]{25})"
    const val ID_CARD_TD_1_LINE_2_REGEX ="([0-9]{6})([0-9]{1})([M|F|X|<]{1})([0-9]{6})([0-9]{1})([A-Z]{3})([A-Z0-9<]{11})([0-9]{1})"
    const val ID_CARD_TD_1_LINE_3_REGEX =  "([A-Z0-9<]{30})"

    const val PASSPORT_TD_3_LINE_1_REGEX = "(P[A-Z0-9<]{1})([A-Z]{3})([A-Z0-9<]{39})"
    const val PASSPORT_TD_3_LINE_2_REGEX = "([A-Z0-9<]{9})([0-9]{1})([A-Z]{3})([0-9]{6})([0-9]{1})([M|F|X|<]{1})([0-9]{6})([0-9]{1})([A-Z0-9<]{14})([0-9<]{1})([0-9]{1})"

}