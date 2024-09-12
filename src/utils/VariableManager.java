package utils;

import java.util.Map;

/**
 * Class for managing variables.
 */
public class VariableManager {
    private final Map<String, Integer> variablesMap;

    public VariableManager(Map<String, Integer> variablesMap) {
        this.variablesMap = variablesMap;
    }

    /**
     * Method for updating the values of variables.
     *
     * @param input a string containing the new value for the variable
     * @throws ArithmeticException exception for invalid input
     */
    public void updateVariable(String input) throws ArithmeticException {
        String[] parts = input.split("=");

        if (parts.length != 2) {
            throw new ArithmeticException("Invalid variable input. Expected format: name = value.");
        }

        String variableName = parts[0].trim();
        String variableValueStr = parts[1].trim();

        if (!variablesMap.containsKey(variableName)) {
            throw new ArithmeticException("Variable " + variableName + " not found.");
        }

        try {
            int variableValue = Integer.parseInt(variableValueStr);
            SolveExpression.validateNumber(variableValue);
            variablesMap.put(variableName, variableValue);
            System.out.println("New value for variable " + variableName + ": " + variableValue);
        } catch (NumberFormatException ex) {
            throw new ArithmeticException("Variable value must be an integer.");
        }
    }
}
