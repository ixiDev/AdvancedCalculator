package example.ixidev.advancedcalculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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


    public static final String parse_tokens_regex = "(\\d+(\\.\\d+)?)|([+*×÷/-])";

    /**
     * mathematical expression that will be calcul
     */
    private String expression = "";

    /**
     * This function is a call of all functions of this class.
     *
     * @param expression : math expression
     * @return double : result of expression
     */
    public Double getResult(String expression) {
        this.expression = expression;
        List<String> tokens = parseTokens(expression);
        Queue<String> postfix = convertToPostFix(tokens);
        return computeResult(postfix);
    }

    /**
     * This is the second important function in Shunting-Yard algorithm ,
     * it's take math expression in post-fix form and calcul it.
     *
     * @param queue math expression elements in post-fix form
     * @return the final result of expression
     */
    public double computeResult(Queue<String> queue) {

        Stack<String> stack = new Stack<>();

        while (!queue.isEmpty()) {
            String token = queue.poll();
            if (isNumber(token)) {
                stack.push(token);
            } else if (isOperation(token)) {
                String firs = stack.pop();
                String second = "0.0";
                if (!stack.isEmpty())
                    second = stack.pop();
                double result = calcul(firs, second, token);
                stack.add(String.valueOf(result));
            }
        }

        double pop = Double.parseDouble(stack.pop());
        String result = String.format(Locale.US, "%.6f", pop);
        return Double.parseDouble(result);
    }

    /**
     * Calculate two numbers and return result
     *
     * @param firs:     first number
     * @param second    : second number
     * @param operation : calculation operation
     * @return result of calculation
     */
    private double calcul(String firs, String second, String operation) {
        double result = 0;
        switch (operation) {
            case "+":
                result = Double.parseDouble(firs) + Double.parseDouble(second);
                break;
            case "-":
                result = Double.parseDouble(second) - Double.parseDouble(firs);
                break;
            case "×":
            case "*":
                result = Double.parseDouble(firs) * Double.parseDouble(second);
                break;
            case "÷":
            case "/":
                result = Double.parseDouble(second) / Double.parseDouble(firs);
                break;
        }
        return result;
    }

    /**
     * This is the important function of the Shunting-Yard algorithm.
     * Convert elements of math expression from infix notation ( normal expression) to post-fix notation
     * Example : [2,+,2] is a infix will convert it to [2,2,+]
     *
     * @param tokens math expression elements in infix form
     * @return list of elements( tokens ) in post-fix form
     * @see: unit test for more examples
     */
    public Queue<String> convertToPostFix(List<String> tokens) {

        Stack<String> stack = new Stack<>();
        Queue<String> queue = new LinkedList<>();

        for (String token : tokens) {
            // first rule :
            if (isNumber(token)) {
                queue.add(token);
            } else if (isOperation(token)) {
                if (!stack.isEmpty()) {
                    // 3 rule
                    String top = stack.peek();
                    if (hasLowPriority(token, top)) {
                        while (!stack.isEmpty()) {
                            queue.add(stack.pop());
                        }
                    }
                }
                stack.push(token); // 2 rule
            }
        }

        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
        return queue;
    }

    /**
     * Compare priority of tow operations
     *
     * @param token : first operation
     * @param top   : second operation
     * @return : true if top has low priority than token
     */
    private boolean hasLowPriority(String token, String top) {
        if (top == null)
            return false;
        List<String> priority = Arrays.asList("*", "×", "/", "÷", "-", "+");
        return priority.indexOf(top) < priority.indexOf(token);
    }

    /**
     * Check whatever string is a math operations or not
     * operations : [+ * / -]
     *
     * @param token : expression element
     * @return true if is operation
     */
    private boolean isOperation(String token) {
        if (token == null)
            return false;
        return token.matches("[+*/×÷-]");
    }

    /**
     * Check whatever string is a number or not
     *
     * @param token : expression element
     * @return true if is number
     */
    private boolean isNumber(String token) {
        if (token == null)
            return false;
        return token.matches("(\\d+(\\.\\d+)?)");
    }

    /**
     * function take mathematical expression as single string and return list of it elements (tokens)
     *
     * @param expression : mathematical expression
     * @return list of expression elements
     */
    public List<String> parseTokens(String expression) {
        Pattern pattern = Pattern.compile(parse_tokens_regex);
        Matcher matcher = pattern.matcher(expression);
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

}