package astTree;

import java.util.Map;

/**
 * Абстрактный класс- нода
 */
public abstract class AstNode {

    /**
     * Метод для вывода AST дерева
     * @param prefix отступ
     */
    public abstract void print(String prefix);

    /**
     * Метод для подсчета значений в ветвях дерева
     * @param variables переменные выражения
     * @return результат вычисления выражения
     */
    public abstract int evaluate(Map<String, Integer> variables);
}
