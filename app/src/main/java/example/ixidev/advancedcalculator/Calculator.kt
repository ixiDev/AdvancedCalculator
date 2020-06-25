package example.ixidev.advancedcalculator

import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * Created by ABDELMAJID ID ALI on 19/06/2020.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class Calculator {
    /**
     * a regular expression ( Regex ) to split mathematical expression element by element
     * example : 2+4-6/8.9+83 split it to list of elements (tokens) -> ["2","+","4","-","6","/","8.9","+","83"]
     */
    private val parse_tokens_regex = "([()])|(\\d+(\\.\\d+)?)|([+*×÷/-])"

    /**
     * mathematical expression that will be calcul
     */
    private var expression: String = ""

    /**
     * Throw when a math expression not correct
     * @param message : error message
     */
    class MathExpressionException(message: String?) : Exception(message)

    /**
     * This function is a call of all functions of this class.
     * @param expression : math expression
     * @return double : result of expression
     */
    @Throws(MathExpressionException::class)
    fun getResult(expression: String): Double {
        this.expression = expression
        val tokens = parseTokens(expression)
        val postfix = convertToPostFix(tokens)
        val result = computeResult(postfix)
        return result
    }

    /**
     *This is the second important function in Shunting-Yard algorithm ,
     * it's take math expression in post-fix form and calcul it.
     * @param queue math expression elements in post-fix form
     * @return the final result of expression
     */
    fun computeResult(queue: Queue<String>): Double {

        val stack = Stack<String>()
        while (queue.isNotEmpty()) {
            val token = queue.poll()
            if (isNumber(token)) {
                stack.push(token)
            } else if (isOperation(token)) {
                val firs = stack.pop() ?: "0.0"
                var second = "0.0"
                if (stack.isNotEmpty())
                    second = stack.pop()
                val result = calcul(firs, second, token)
                stack.add(result.toString())
            }
        }

        val pop = stack.pop().toDouble()
        val result = String.format("%.6f", pop)
        return result.toDouble()
    }

    /**
     * Calculate two numbers and return result
     * @param firs: first number
     * @param second : second number
     * @param operation : calculation operation
     * @return result of calculation
     */
    private fun calcul(firs: String, second: String, operation: String?): Double {
        return when (operation) {
            "+" -> firs.toDouble() + second.toDouble()
            "-" -> second.toDouble() - firs.toDouble()
            "*", "×" -> firs.toDouble() * second.toDouble()
            "/", "÷" -> second.toDouble() / firs.toDouble()
            else -> 0.0
        }

    }

    /**
     * This is the important function of the Shunting-Yard algorithm.
     * Convert elements of math expression from infix notation ( normal expression) to post-fix notation
     * Example : [2,+,2] is a infix will convert it to [2,2,+]
     * @see CalculatorTest unit test for more examples
     * @param tokens math expression elements in infix form
     * @return list of elements( tokens ) in post-fix form
     */
    @Throws(MathExpressionException::class)
    fun convertToPostFix(tokens: List<String>): Queue<String> {
        val stack = Stack<String>()
        val queue: Queue<String> = LinkedList()

        for (token in tokens) {
            when {
                isNumber(token) -> {
                    queue.add(token) // first rule
                }
                isOperation(token) -> {
                    if (stack.isNotEmpty()) {
                        val top = stack.peek()
                        // rule 3
                        if (hasLowPriority(token, top)) {
                            while (stack.isNotEmpty() && stack.peek() != "(") {
                                queue.add(stack.pop())
                            }
                        }
                    }
                    stack.push(token) // rule 2

                }
                token == "(" -> {
                    stack.push(token)
                }
                token == ")" -> {
                    if (stack.isEmpty())
                        fail(token)
                    while (stack.peek() != "(") {
                        queue.add(stack.pop())
                        if (stack.isEmpty())
                            fail(token)
                    }
                    if (stack.peek() != "(") {
                        fail(token)
                    }
                    stack.pop() // remove the opened bracket
                }
            }

        }

        while (stack.isNotEmpty()) {
            queue.add(stack.pop())
        }
        return queue
    }

    /**
     * Throw Brackets mismatch exception
     */
    private fun fail(token: String) {
        throw MathExpressionException(" Brackets mismatch error at \'$token\'")
    }

    /**
     * Compare priority of tow operations
     * @param token : first operation
     * @param top : second operation
     * @return : true if top has low priority than token
     */
    private fun hasLowPriority(token: String, top: String?): Boolean {
        if (top == null)
            return false
        val priority = listOf("*", "×", "/", "÷", "-", "+")
        return priority.indexOf(top) < priority.indexOf(token)
    }

    /**
     *Check whatever string is a math operations or not
     * operations : [+ * / -]
     * @param token : expression element
     * @return true if is operation
     */
    private fun isOperation(token: String?): Boolean {
        if (token == null)
            return false
        return token.matches("[+*/×÷-]".toRegex())
    }

    /**
     * Check whatever string is a number or not
     * @param token : expression element
     * @return true if is number
     */
    private fun isNumber(token: String?): Boolean {
        if (token == null)
            return false
        return token.matches("(\\d+(\\.\\d+)?)".toRegex())
    }

    /**
     * function take mathematical expression as single string and return list of it elements (tokens)
     * @param expression : mathematical expression
     * @return list of expression elements
     */
    fun parseTokens(expression: String): List<String> {
        val pattern = Pattern.compile(parse_tokens_regex)
        val matcher = pattern.matcher(expression)
        val tokens = ArrayList<String>()

        while (matcher.find()) {
            tokens.add(matcher.group())
        }
        return tokens
    }

    fun isExpressionCorrect(expression: String): Boolean {
        return expression.matches("(([()]*[+*×÷/-])?[()]*(\\d+(\\.\\d+)?)[()]*)+".toRegex())
    }

}