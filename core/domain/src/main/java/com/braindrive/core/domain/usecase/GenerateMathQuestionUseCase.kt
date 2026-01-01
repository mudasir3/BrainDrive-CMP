package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.Difficulty
import com.braindrive.core.domain.model.MathOperation
import com.braindrive.core.domain.model.MathQuestion
import javax.inject.Inject

class GenerateMathQuestionUseCase @Inject constructor() {
    operator fun invoke(difficulty: Difficulty = Difficulty.MEDIUM): MathQuestion {
        val (range1, range2) = when (difficulty) {
            Difficulty.EASY -> (1..20) to (1..20)
            Difficulty.MEDIUM -> (1..99) to (1..99)
            Difficulty.HARD -> (10..999) to (10..999)
        }
        
        var number1 = range1.random().let { if (it == 0 || it == 2) it + 1 else it }
        var number2 = range2.random().let { if (it == 0) it + 1 else it }
        
        // For hard difficulty, include more complex operations
        val operations = when (difficulty) {
            Difficulty.EASY -> listOf(MathOperation.ADDITION, MathOperation.SUBTRACTION)
            Difficulty.MEDIUM -> MathOperation.values().toList()
            Difficulty.HARD -> MathOperation.values().toList()
        }
        
        val operation = operations.random()
        
        // Ensure division results in whole numbers for easier difficulty
        if (operation == MathOperation.DIVISION && difficulty != Difficulty.HARD) {
            number2 = (1..number1).filter { number1 % it == 0 }.randomOrNull() ?: 1
        }
        
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

