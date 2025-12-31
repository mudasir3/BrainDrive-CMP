package com.braindrive.core.domain.model

data class MathQuestion(
    val number1: Int,
    val number2: Int,
    val operation: MathOperation,
    val result: Float
) {
    val displayText: String
        get() = "$number1 ? $number2 = ${result.toInt()}"
}

enum class MathOperation(val symbol: Char) {
    ADDITION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    DIVISION('/')
}

fun MathOperation.toChar(): Char = symbol

