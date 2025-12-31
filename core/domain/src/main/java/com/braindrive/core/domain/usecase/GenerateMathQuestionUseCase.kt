package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.MathOperation
import com.braindrive.core.domain.model.MathQuestion
import javax.inject.Inject

class GenerateMathQuestionUseCase @Inject constructor() {
    operator fun invoke(): MathQuestion {
        val number1 = (1..99).random().let { if (it == 0 || it == 2) it + 1 else it }
        val number2 = (1..99).random().let { if (it == 0) it + 1 else it }
        
        val operation = MathOperation.values().random()
        val result = when (operation) {
            MathOperation.ADDITION -> (number1 + number2).toFloat()
            MathOperation.SUBTRACTION -> (number1 - number2).toFloat()
            MathOperation.MULTIPLICATION -> (number1 * number2).toFloat()
            MathOperation.DIVISION -> (number1.toFloat() / number2)
        }
        
        return MathQuestion(
            number1 = number1,
            number2 = number2,
            operation = operation,
            result = result
        )
    }
}

