package astTree;

import java.util.Map;

/**
 * Abstract class representing a node in the AST.
 */
public abstract class AstNode {

    /**
     * Method for printing the AST tree.
     * @param prefix indentation
     */
    public abstract void print(String prefix);

    /**
     * Method for evaluating the values in the branches of the tree.
     * @param variables the variables of the expression
     * @return the result of the expression evaluation
     */
    public abstract int evaluate(Map<String, Integer> variables);
}
