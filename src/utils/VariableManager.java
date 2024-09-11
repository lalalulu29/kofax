package utils;

import java.util.Map;

/**
 * Класс для работы с переменными
 */
public class VariableManager {
    private final Map<String, Integer> variablesMap;

    public VariableManager(Map<String, Integer> variablesMap) {
        this.variablesMap = variablesMap;
    }

    /**
     * Метод для обновления значений переменных
     *
     * @param input строка - новое значение для переменной
     * @throws ArithmeticException исключение о некорректности входных данных
     */
    public void updateVariable(String input) throws ArithmeticException {
        String[] parts = input.split("=");

        if (parts.length != 2) {
            throw new ArithmeticException("Некорректный ввод переменной. Ожидалось: имя = значение.");
        }

        String variableName = parts[0].trim();
        String variableValueStr = parts[1].trim();

        if (!variablesMap.containsKey(variableName)) {
            throw new ArithmeticException("Переменная " + variableName + " не найдена.");
        }

        try {
            int variableValue = Integer.parseInt(variableValueStr);
            SolveExpression.validateNumber(variableValue);
            variablesMap.put(variableName, variableValue);
            System.out.println("Новое значение переменной " + variableName + ": " + variableValue);
        } catch (NumberFormatException ex) {
            throw new ArithmeticException("Значение переменной должно быть целым числом.");
        }
    }
}
