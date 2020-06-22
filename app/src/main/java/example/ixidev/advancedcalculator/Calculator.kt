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

    private val parse_tokens_regex = "(\\d+(\\.\\d+)?)|([+*/-])"
    private var expression: String = ""

    // 2+4  -> 2,+,4
    fun getResult(expression: String): Double {
        this.expression = expression
        val tokens = parseTokens(expression)
        val postfix = convertToPostFix(tokens)
        val result = computeResult(postfix)
        return 0.0
    }

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

        val result = stack.pop()
        return result.toDouble()
    }

    private fun calcul(firs: String, second: String, operation: String): Double {
        return when (operation) {
            "+" -> firs.toDouble() + second.toDouble()
            "-" -> second.toDouble() - firs.toDouble()
            "*" -> firs.toDouble() * second.toDouble()
            "/" -> second.toDouble() / firs.toDouble()
            else -> 0.0
        }

    }

    fun convertToPostFix(tokens: List<String>): Queue<String> {
        val stack = Stack<String>()
        val queue: Queue<String> = LinkedList()

        for (token in tokens) {
            // first rule :
            if (isNumber(token)) {
                queue.add(token)
            } else if (isOperation(token)) {
                if (stack.isEmpty()) {
                    stack.push(token) // 2 rule
                } else {
                    // 3 rule
                    val top = stack.peek()
                    if (hasLowPriority(token, top)) {
                        while (stack.isNotEmpty()) {
                            queue.add(stack.pop())
                        }
                        stack.push(token)
                    } else stack.push(token)
                }
            }
        }

        while (stack.isNotEmpty()) {
            queue.add(stack.pop())
        }
        return queue
    }

    private fun hasLowPriority(token: String, top: String?): Boolean {
        if (top == null)
            return false
        val priority = listOf("*", "/", "+", "-")
        return priority.indexOf(top) < priority.indexOf(token)
    }

    private fun isOperation(token: String): Boolean {
        return token.matches("[+*/-]".toRegex())
    }

    private fun isNumber(token: String): Boolean {
        return token.matches("(\\d+(\\.\\d+)?)".toRegex())
    }

    fun parseTokens(expression: String): List<String> {
        val pattern = Pattern.compile(parse_tokens_regex)
        val matcher = pattern.matcher(expression)
        val tokens = ArrayList<String>()

        while (matcher.find()) {
            tokens.add(matcher.group())
        }
        return tokens
    }


}