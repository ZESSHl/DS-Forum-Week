public class InfixToPostfixRecursive {
    private String input;
    private int index;

    public String convert(String infix) {
        input = infix.replaceAll("\\s+", "");
        index = 0;

        String postfix = parseExpression();

        if (index != input.length()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return postfix;
    }

    // Handles + and -
    private String parseExpression() {
        String left = parseTerm();

        while (index < input.length() &&
              (input.charAt(index) == '+' || input.charAt(index) == '-')) {
            char op = input.charAt(index++);
            String right = parseTerm();
            left = left + right + op;
        }

        return left;
    }

    // Handles * and /
    private String parseTerm() {
        String left = parseFactor();

        while (index < input.length() &&
              (input.charAt(index) == '*' || input.charAt(index) == '/')) {
            char op = input.charAt(index++);
            String right = parseFactor();
            left = left + right + op;
        }

        return left;
    }

    // Handles operands and parentheses
    private String parseFactor() {
        if (index >= input.length()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        char ch = input.charAt(index);

        // Parenthesized expression
        if (ch == '(') {
            index++; // skip '('
            String inside = parseExpression();

            if (index >= input.length() || input.charAt(index) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }

            index++; // skip ')'
            return inside;
        }

        // Operand (single letter or digit)
        if (Character.isLetterOrDigit(ch)) {
            index++;
            return String.valueOf(ch);
        }

        throw new IllegalArgumentException("Invalid character: " + ch);
    }

    public static void main(String[] args) {
        InfixToPostfixRecursive converter = new InfixToPostfixRecursive();

        String infix1 = "A+B*C";
        String infix2 = "(A+B)*C";
        String infix3 = "A+B*(C-D)";

        System.out.println(infix1 + " -> " + converter.convert(infix1)); // ABC*+
        System.out.println(infix2 + " -> " + converter.convert(infix2)); // AB+C*
        System.out.println(infix3 + " -> " + converter.convert(infix3)); // ABCD-*+
    }
}