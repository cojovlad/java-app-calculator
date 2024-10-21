import java.util.Scanner;
import java.util.ArrayList;

public class Calculator {
    private static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double result = 0;
        char operator;
        boolean exit = false;

        System.out.println("Basic Calculator with History (type 'h' to see history, 'e' to exit)");

        while (!exit) {
            System.out.println("Enter first number: ");
            double num1 = scanner.nextDouble();

            System.out.println("Enter operator (+, -, *, /): ");
            operator = scanner.next().charAt(0);

            System.out.println("Enter second number: ");
            double num2 = scanner.nextDouble();

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        System.out.println("Cannot divide by zero.");
                        continue;
                    }
                    break;
                default:
                    System.out.println("Invalid operator.");
                    continue;
            }

            System.out.println("Result: " + result);
            saveToHistory(num1, operator, num2, result);

            System.out.println("Do you want to perform another operation? (y/n) or type 'h' for history or 'e' to exit:");
            char choice = scanner.next().charAt(0);

            if (choice == 'n') {
                exit = true;
            } else if (choice == 'h') {
                showHistory();
            } else if (choice == 'e') {
                exit = true;
            }
        }

        System.out.println("Thank you for using the calculator!");
        scanner.close();
    }

    private static void saveToHistory(double num1, char operator, double num2, double result) {
        String entry = num1 + " " + operator + " " + num2 + " = " + result;
        history.add(entry);
    }

    private static void showHistory() {
        System.out.println("\n--- Calculation History ---");
        if (history.isEmpty()) {
            System.out.println("No history available.");
        } else {
            for (String entry : history) {
                System.out.println(entry);
            }
        }
        System.out.println("---------------------------\n");
    }
}
