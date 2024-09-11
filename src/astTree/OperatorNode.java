package astTree;

import java.util.Map;

/**
 * Класс- нода оператора выражения
 */
public class OperatorNode extends AstNode {
    private final char operator;
    private final AstNode leftBranch;
    private final AstNode rightBranch;

    public OperatorNode(char operator, AstNode leftBranch, AstNode rightBranch) {
        this.operator = operator;
        this.leftBranch = leftBranch;
        this.rightBranch = rightBranch;
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix + "Operator: " + operator);
        leftBranch.print(prefix + "  ");
        rightBranch.print(prefix + "  ");
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        int leftValue = leftBranch.evaluate(variables);
        int rightValue = rightBranch.evaluate(variables);

        switch (operator) {
            case '+':
                return leftValue + rightValue;
            case '-':
                return leftValue - rightValue;
            case '*':
                return leftValue * rightValue;
            case '/':
                return leftValue / rightValue;
            default:
                throw new UnsupportedOperationException("Неподдерживаемый оператор: " + operator);
        }
    }
}
