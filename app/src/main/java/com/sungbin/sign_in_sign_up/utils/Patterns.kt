package com.sungbin.sign_in_sign_up.utils

import java.util.regex.Pattern

object Patterns {
    val pPattern = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*[0-9]).{5,12}.\$",
        Pattern.CASE_INSENSITIVE
    )
}