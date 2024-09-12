package astTree;

import java.util.Map;

/**
 * Class representing a number node in the expression.
 */
public class NumberNode extends AstNode {
    private final int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix + "Number: " + value);
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        return value;
    }
}
