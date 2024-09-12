package utils;

import java.util.Scanner;

/**
 * Class for obtaining input from the user via the terminal.
 */
public class UserInputHandler {
    private final Scanner scanner;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Method for getting an arithmetic expression from the user via the terminal.
     *
     * @return the arithmetic expression as a string
     */
    public String getExpression() {
        System.out.print("Enter an arithmetic expression with (or without) variables: ");
        return scanner.nextLine();
    }

    /**
     * Method for displaying a prompt to the user after receiving the arithmetic expression.
     *
     * @return the user's command
     */
    public String getCommand() {
        System.out.println("\nEnter commands or new values for variables (e.g., x = 4):\n" +
                "Supported commands:\n" +
                "calc - re-evaluate the expression\n" +
                "print - print the AST of the given arithmetic expression\n");

        return scanner.nextLine();
    }
}
