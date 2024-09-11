package utils;

import astTree.AstNode;

import java.util.Map;
import java.util.Random;

/**
 * Класс обертка над логикой вычисления вырожения
 */
public class ExpressionEvaluator {
    private final AstNode node;
    private final Map<String, Integer> variablesMap;

    public ExpressionEvaluator(AstNode node, Map<String, Integer> variablesMap) {
        this.node = node;
        this.variablesMap = variablesMap;
    }

    /**
     * Метод для подстановки случайных значений в переменные
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
     * Метод для вычисления значения ноды
     * @return значение ноды
     */
    public int evaluate() {
        return node.evaluate(variablesMap);
    }

    /**
     * Метод для вывода значений переменных
     */
    public void printVariables() {
        if (!variablesMap.isEmpty()) {
            System.out.println("Значение переменных:");
            variablesMap.forEach((name, value) -> System.out.println(name + ": " + value));
        }
    }

    /**
     * Метод для печати ноды
     */
    public void printAst() {
        node.print("");
    }
}
