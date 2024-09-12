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
        System.out.println("Calculation result: " + evaluator.evaluate());
        evaluator.printVariables();

        processCommands(inputHandler, evaluator, variableManager);
    }

    /**
     * Method for safely parsing the expression.
     *
     * @param expression   the expression
     * @param variablesMap the map containing variables and their values
     * @return the AST node
     */
    private static AstNode parseExpression(String expression, Map<String, Integer> variablesMap) {
        try {
            return SolveExpression.parse(expression, variablesMap);
        } catch (ArithmeticException ex) {
            System.out.println("Error in expression: " + ex.getMessage() + ". Please try again.");
            return null;
        }
    }

    /**
     * Method for processing user commands.
     *
     * @param inputHandler    parameter for getting user input
     * @param evaluator       parameter for evaluating the expression
     * @param variableManager parameter for managing variables
     */
    private static void processCommands(UserInputHandler inputHandler, ExpressionEvaluator evaluator,
                                        VariableManager variableManager) {
        String command = inputHandler.getCommand();

        switch (command) {
            case "calc":
                int result = evaluator.evaluate();
                System.out.println("Calculation result: " + result + "\n");
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
                            ex.getMessage() != null ? ex.getMessage() : "Invalid command or variable.");
                    processCommands(inputHandler, evaluator, variableManager);
                }
        }
    }
}
