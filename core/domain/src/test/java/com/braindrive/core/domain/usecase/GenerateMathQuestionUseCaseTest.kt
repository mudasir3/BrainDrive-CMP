package com.braindrive.core.domain.usecase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GenerateMathQuestionUseCaseTest {
    
    private lateinit var useCase: GenerateMathQuestionUseCase
    
    @Before
    fun setup() {
        useCase = GenerateMathQuestionUseCase()
    }
    
    @Test
    fun `generate question returns valid question`() {
        val question = useCase()
        
        assertNotNull(question)
        assertTrue(question.number1 > 0)
        assertTrue(question.number2 > 0)
        assertNotNull(question.operation)
    }
    
    @Test
    fun `generate question calculates correct result for addition`() {
        repeat(100) {
            val question = useCase()
            if (question.operation.symbol == '+') {
                val expected = question.number1 + question.number2
                assertEquals(expected.toFloat(), question.result, 0.01f)
            }
        }
    }
    
    @Test
    fun `generate question calculates correct result for subtraction`() {
        repeat(100) {
            val question = useCase()
            if (question.operation.symbol == '-') {
                val expected = question.number1 - question.number2
                assertEquals(expected.toFloat(), question.result, 0.01f)
            }
        }
    }
    
    @Test
    fun `generate question calculates correct result for multiplication`() {
        repeat(100) {
            val question = useCase()
            if (question.operation.symbol == '*') {
                val expected = question.number1 * question.number2
                assertEquals(expected.toFloat(), question.result, 0.01f)
            }
        }
    }
    
    @Test
    fun `generate question calculates correct result for division`() {
        repeat(100) {
            val question = useCase()
            if (question.operation.symbol == '/') {
                val expected = question.number1.toFloat() / question.number2
                assertEquals(expected, question.result, 0.01f)
            }
        }
    }
}

