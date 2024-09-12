package astTree;

import java.util.Map;

/**
 * Class representing a variable node in the expression.
 */
public class VariableNode extends AstNode {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix + "Variable: " + name);
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        if (!variables.containsKey(name) || variables.get(name) == null) {
            throw new IllegalArgumentException("Переменная " + name + " не определена.");
        }
        return variables.get(name);
    }
}
