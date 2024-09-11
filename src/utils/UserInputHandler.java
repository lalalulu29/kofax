package utils;

import java.util.Scanner;

/**
 * Класс для получения данных от пользователя через терминал
 */
public class UserInputHandler {
    private final Scanner scanner;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Метод для получения арифмитического выражения из терминала пользователя
     *
     * @return арифметическое выражение ввиде строки
     */
    public String getExpression() {
        System.out.print("Введите арифметическое выражение с (без) переменными: ");
        return scanner.nextLine();
    }

    /**
     * Метод для вывода подсказки пользователю, после получения от пользователя арифм. выражения
     *
     * @return Команда от пользователя
     */
    public String getCommand() {
        System.out.println("\nВведите команды или новые значения для переменных (например, x = 4):\n" +
                "Поддерживаемые команды:\n" +
                "calc - занова произвести вычисление\n" +
                "print - вывести AST дерево данного арифметического выражения\n");

        return scanner.nextLine();
    }
}