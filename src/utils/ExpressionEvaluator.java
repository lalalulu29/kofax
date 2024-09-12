package utils;

import astTree.AstNode;

import java.util.Map;
import java.util.Random;

/**
 * A wrapper class for expression evaluation logic.
 */
public class ExpressionEvaluator {
    private final AstNode node;
    private final Map<String, Integer> variablesMap;

    public ExpressionEvaluator(AstNode node, Map<String, Integer> variablesMap) {
        this.node = node;
        this.variablesMap = variablesMap;
    }

    /**
     * Method for assigning random values to variables.
     */
    public void assignRandomValuesToVariables() {
        Random random = new Random();
        for (Map.Entry<String, Integer> entry : variablesMap.entrySet()) {
            if (entry.getValue() == null) {
                entry.setValue(random.nextInt(65535) + 1);
            }
        }
    }

    /**
     * Method for evaluating the value of the node.
     * @return the node's value.
     */
    public int evaluate() {
        return node.evaluate(variablesMap);
    }

    /**
     * Method for printing variable values.
     */
    public void printVariables() {
        if (!variablesMap.isEmpty()) {
            System.out.println("Variable values:");
            variablesMap.forEach((name, value) -> System.out.println(name + ": " + value));
        }
    }

    /**
     * Method for printing the AST node.
     */
    public void printAst() {
        node.print("");
    }
}
