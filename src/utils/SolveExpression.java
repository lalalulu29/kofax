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
 * Класс для решения арифметического выражения
 */
public class SolveExpression {

    private final static List<Character> SUPPORTED_ARITHMETIC_OPERATORS = Arrays.asList('+', '-', '*', '/');

    /**
     * Метод для оценки выражения
     *
     * @param expression строка, представляющая из себя арифметическое выражение
     * @return результат решения арифметического выражения
     * @throws ArithmeticException при неверном формате выражения
     */
    public static AstNode parse(String expression, Map<String, Integer> variablesSet) throws ArithmeticException {
        expression = expression.replaceAll(" ", "");

        if (expression.isEmpty()) {
            throw new ArithmeticException("Выражение не должно быть пустым!");
        }

        Stack<AstNode> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length()) {
                    if (Character.isLetter(expression.charAt(i))) {
                        throw new ArithmeticException("Наименование переменной не может начинаться с числа!");
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
                        throw new ArithmeticException("Некорректное выражение: недостаточно операндов для операции!");
                    }
                    numbers.push(buildOperatorNode(operators.pop(), numbers.pop(), numbers.pop()));
                }
                if (operators.isEmpty() || operators.peek() != '(') {
                    throw new ArithmeticException("Некорректное выражение: пропущена открывающая скобка!");
                }
                operators.pop();

            } else if (isOperator(ch)) {
                if (numbers.isEmpty()) {
                    throw new ArithmeticException("Некорректное выражение: оператор '" + ch + "' без операндов!");
                }
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                    if (numbers.size() < 2) {
                        throw new ArithmeticException("Некорректное выражение: недостаточно операндов для операции!");
                    }
                    numbers.push(buildOperatorNode(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(ch);

            } else {
                throw new ArithmeticException("Недопустимый символ в выражении: " + ch);
            }
        }

        while (!operators.isEmpty()) {
            if (numbers.size() < 2) {
                throw new ArithmeticException("Некорректное выражение: недостаточно операндов для операции!");
            }
            numbers.push(buildOperatorNode(operators.pop(), numbers.pop(), numbers.pop()));
        }

        if (numbers.size() != 1) {
            throw new ArithmeticException("Некорректное выражение: неверное количество операндов и операторов.");
        }

        return numbers.pop();
    }

    /**
     * Построение узла для оператора
     *
     * @param operator оператор ноды
     * @param right правый наслденик ноды
     * @param left левый наследний ноды
     * @return нода AST деерва
     */
    private static AstNode buildOperatorNode(char operator, AstNode right, AstNode left) {
        return new OperatorNode(operator, left, right);
    }

    /**
     * Метод для определения приоритета операций
     *
     * @param operator оператор
     * @return приоритет операции
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
     * Метод для проверки, является ли символ оператором
     *
     * @param ch символ
     * @return булево значение, является ли входной символ оператором
     */
    private static boolean isOperator(char ch) {
        return SUPPORTED_ARITHMETIC_OPERATORS.contains(ch);
    }

    /**
     * Метод для валидации числа, соответствует ли оно условиям задачи
     * @param number число
     */
    public static void validateNumber(int number) {
        if (number == 0) {
            throw new ArithmeticException("0 не является положительным числом!");
        }
        if (number < 0 || number > 65535) {
            throw new ArithmeticException("Число размерности больше 16 бит");
        }
    }
}