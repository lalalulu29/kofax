package utils;

import astTree.AstNode;
import astTree.NumberNode;
import astTree.OperatorNode;
import astTree.VariableNode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Class for solving an arithmetic expression.
 */
public class SolveExpression {

    private final static List<Character> SUPPORTED_ARITHMETIC_OPERATORS = Arrays.asList('+', '-', '*', '/');

    /**
     * Method for evaluating the expression.
     *
     * @param expression the string representing an arithmetic expression
     * @return the result of solving the arithmetic expression
     * @throws ArithmeticException if the expression format is invalid
     */
    public static AstNode parse(String expression, Map<String, Integer> variablesSet) throws ArithmeticException {
        expression = expression.replaceAll(" ", "");

        if (expression.isEmpty()) {
            throw new ArithmeticException("The expression must not be empty!");
        }

        Stack<AstNode> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length()) {
                    if (Character.isLetter(expression.charAt(i))) {
                        throw new ArithmeticException("Variable names cannot start with a digit!");
                    } else if (!Character.isDigit(expression.charAt(i))) {
                        break;
                    }
                    sb.append(expression.charAt(i++));
                }
                i--;
                int number = Integer.parseInt(sb.toString());
                validateNumber(number);
                numbers.push(new NumberNode(number));

            } else if (Character.isLetter(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && Character.isLetterOrDigit(expression.charAt(i))) {
                    sb.append(expression.charAt(i++));
                }
                i--;
                String variableName = sb.toString();
                variablesSet.put(variableName, null);
                numbers.push(new VariableNode(variableName));

            } else if (ch == '(') {
                operators.push(ch);

            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    if (numbers.size() < 2) {
                        throw new ArithmeticException("Invalid expression: not enough operands for the operation!");
                    }
                    numbers.push(buildOperatorNode(operators.pop(), numbers.pop(), numbers.pop()));
                }
                if (operators.isEmpty() || operators.peek() != '(') {
                    throw new ArithmeticException("Invalid expression: missing opening parenthesis!");
                }
                operators.pop();

            } else if (isOperator(ch)) {
                if (numbers.isEmpty()) {
                    throw new ArithmeticException("Invalid expression: operator '" + ch + "' without operands!");
                }
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                    if (numbers.size() < 2) {
                        throw new ArithmeticException("Invalid expression: not enough operands for the operation!");
                    }
                    numbers.push(buildOperatorNode(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(ch);

            } else {
                throw new ArithmeticException("Invalid character in the expression: " + ch);
            }
        }

        while (!operators.isEmpty()) {
            if (numbers.size() < 2) {
                throw new ArithmeticException("Invalid expression: not enough operands for the operation!");
            }
            numbers.push(buildOperatorNode(operators.pop(), numbers.pop(), numbers.pop()));
        }

        if (numbers.size() != 1) {
            throw new ArithmeticException("Invalid expression: incorrect number of operands and operators.");
        }

        return numbers.pop();
    }

    /**
     * Builds an operator node.
     *
     * @param operator the operator of the node
     * @param right the right child of the node
     * @param left the left child of the node
     * @return an AST node
     */
    private static AstNode buildOperatorNode(char operator, AstNode right, AstNode left) {
        return new OperatorNode(operator, left, right);
    }

    /**
     * Method for determining the precedence of operations.
     *
     * @param operator the operator
     * @return the precedence of the operation
     */
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    /**
     * Method to check if a character is an operator.
     *
     * @param ch the character
     * @return a boolean indicating whether the character is an operator
     */
    private static boolean isOperator(char ch) {
        return SUPPORTED_ARITHMETIC_OPERATORS.contains(ch);
    }

    /**
     * Method for validating a number based on problem constraints.
     * @param number the number
     */
    public static void validateNumber(int number) {
        if (number == 0) {
            throw new ArithmeticException("0 is not a positive number!");
        }
        if (number < 0 || number > 65535) {
            throw new ArithmeticException("Number exceeds 16-bit range");
        }
    }
}
