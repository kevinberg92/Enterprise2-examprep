package org.example.wrappedresponses.`rest-exception`

import java.lang.RuntimeException

class UserInputValidationException (
    message: String,
    val httpCode: Int = 400
) : RuntimeException(message)