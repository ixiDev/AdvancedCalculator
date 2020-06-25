package example.ixidev.advancedcalculator

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by ABDELMAJID ID ALI on 21/06/2020.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class CalculatorTest {

    @Test
    fun parseTokens() {
        val calculator = Calculator()
        val tokens = calculator.parseTokens("2+8.5")

        assertEquals(listOf("2", "+", "8.5"), tokens)
    }

    @Test
    fun parseTokens2() {
        val calculator = Calculator()
        val tokens = calculator.parseTokens("2+8.5-99/7")

        assertEquals(listOf("2", "+", "8.5", "-", "99", "/", "7"), tokens)
    }

    @Test
    fun convertToPostFixTest() {
        val calculator = Calculator()
        val tokens = calculator.parseTokens("2+8.5")
        val postFix = calculator.convertToPostFix(tokens)
        assertEquals(listOf("2", "8.5", "+"), postFix)
    }

    @Test
    fun convertToPostFixTest2() {
        val calculator = Calculator()
        val tokens = calculator.parseTokens("6+7-8*2+962/7")
        val postFix = calculator.convertToPostFix(tokens)
        assertEquals(listOf("6", "7", "8", "2", "*", "-", "+", "962", "7", "/", "+"), postFix)
    }

    @Test
    fun computeResultTest() {
        val calculator = Calculator()
        val tokens = calculator.parseTokens("2+8.5-6/2")
        val postFix = calculator.convertToPostFix(tokens)
        val result = calculator.computeResult(postFix)
        assertEquals(7.5, result, 0.0)

    }

    @Test
    fun computeResultTest2() {
        val calculator = Calculator()
        val tokens = calculator.parseTokens("8-5+6")
        val postFix = calculator.convertToPostFix(tokens)
        val result = calculator.computeResult(postFix)
        assertEquals(9.0, result, 0.0)

    }

    @Test
    fun computeResultTest3() {
        val calculator = Calculator()
        val tokens = calculator.parseTokens("6+7-8*2+962/7")
        val postFix = calculator.convertToPostFix(tokens)
        val result = calculator.computeResult(postFix)
        assertEquals(134.428571, result, 0.0)
    }

    @Test
    fun getResult() {
        val calculator = Calculator()
        val result = calculator.getResult("4×77-93÷3-8×99+2+44-(25×54)")
        assertEquals(-1819.0, result, 0.0)
    }

    @Test
    fun isExpressionCorrect() {
        val calculator = Calculator()
        val result = calculator.isExpressionCorrect("4×77-93÷3-8×99+2+44--83+*")
        assertEquals(false, result)
    }
    @Test
    fun isExpressionCorrect2() {
        val calculator = Calculator()
        val result = calculator.isExpressionCorrect("4×77-93÷3-8×99+2+44-(25×54)")
        assertEquals(true, result)
    }

}