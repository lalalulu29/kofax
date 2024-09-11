import astTree.AstNode;
import utils.ExpressionEvaluator;
import utils.SolveExpression;
import utils.UserInputHandler;
import utils.VariableManager;

import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        UserInputHandler inputHandler = new UserInputHandler();
        Map<String, Integer> variablesMap = new HashMap<>();

        AstNode node = null;
        while (node == null) {
            String expression = inputHandler.getExpression();
            node = parseExpression(expression, variablesMap);
        }

        ExpressionEvaluator evaluator = new ExpressionEvaluator(node, variablesMap);
        VariableManager variableManager = new VariableManager(variablesMap);

        evaluator.assignRandomValuesToVariables();
        System.out.println("Результат вычисления: " + evaluator.evaluate());
        evaluator.printVariables();

        processCommands(inputHandler, evaluator, variableManager);
    }

    /**
     * Метод для безопасного парсинга выражения
     *
     * @param expression   выражение
     * @param variablesMap map-а содержащая в себе перменные и их значения
     * @return Ast нода
     */
    private static AstNode parseExpression(String expression, Map<String, Integer> variablesMap) {
        try {
            return SolveExpression.parse(expression, variablesMap);
        } catch (ArithmeticException ex) {
            System.out.println("Ошибка в выражении: " + ex.getMessage() + ". Попробуйте снова.");
            return null;
        }
    }

    /**
     * Метод обработки команд от пользователя
     *
     * @param inputHandler    параметр для получение данных от пользователя
     * @param evaluator       параметр для вычисления выражения
     * @param variableManager параметр для работы с переменными
     */
    private static void processCommands(UserInputHandler inputHandler, ExpressionEvaluator evaluator,
                                        VariableManager variableManager) {
        String command = inputHandler.getCommand();

        switch (command) {
            case "calc":
                int result = evaluator.evaluate();
                System.out.println("Результат вычисления: " + result + "\n");
                processCommands(inputHandler, evaluator, variableManager);
                break;
            case "print":
                evaluator.printAst();
                processCommands(inputHandler, evaluator, variableManager);
                break;
            default:
                try {
                    variableManager.updateVariable(command);
                    processCommands(inputHandler, evaluator, variableManager);
                } catch (ArithmeticException ex) {
                    System.out.println(
                            ex.getMessage() != null ? ex.getMessage() : "Некорректная команда или переменная.");
                    processCommands(inputHandler, evaluator, variableManager);
                }
        }
    }
}
