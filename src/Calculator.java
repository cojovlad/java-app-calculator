import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;

public class Calculator {
    private static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Advanced Calculator with History (type 'h' to see history, 'e' to exit)");

        while (!exit) {
            System.out.println("Enter expression (with operators +, -, *, /, and parentheses):");
            String expression = scanner.nextLine();

            if (expression.equals("h")) {
                showHistory();
                continue;
            }

            if (expression.equals("e")) {
                exit = true;
                break;
            }

            try {
                double result = evaluateExpression(expression);
                System.out.println("Result: " + result);
                saveToHistory(expression, result);
            } catch (Exception e) {
                System.out.println("Invalid expression.");
            }

            System.out.println("Do you want to perform another operation? (y/n) or type 'h' for history or 'e' to exit:");
            char choice = scanner.next().charAt(0);
            scanner.nextLine();  // Consume the newline

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

    private static double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // Ignore whitespace
            if (ch == ' ') {
                continue;
            }

            // If it's a digit or part of a number (including decimals)
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(sb.toString()));
                i--; // Adjust index back for non-number character
            }
            // If it's a left parenthesis, push it to operators stack
            else if (ch == '(') {
                operators.push(ch);
            }
            // If it's a right parenthesis, solve entire bracket
            else if (ch == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();  // Remove '('
            }
            // If it's an operator
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek())) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(ch);
            }
        }

        // Apply remaining operators to the numbers stack
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1'
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    // Applies the operation and returns the result
    private static double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero.");
                }
                return a / b;
            default: return 0;
        }
    }

    private static void saveToHistory(String expression, double result) {
        String entry = expression + " = " + result;
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
