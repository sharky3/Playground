import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ReversePolishNotation {
    private static List<String> convertToRPN(String inputString) {
        Stack<String> stack = new Stack<>();
        List<String> convertedString = new ArrayList<>();
        String[] tokenArr = inputString.split("\\s+|((?<=[()*/+-])|(?=[()*/+-]))");
        List<String> tokenList = new ArrayList<>(Arrays.asList(tokenArr));

        if (tokenList.get(0).equals("-")) {
            tokenList.set(0, "-1");
            tokenList.add(1, "*");
        }

        for (int i = 0; i < tokenList.size(); i++) {
                if (tokenList.get(i).matches("-?\\d+")) {
                    convertedString.add(tokenList.get(i));
                    continue;

                } else if (tokenList.get(i).matches("[(*/+-]") && tokenList.get(i + 1).equals("-")) {
                    tokenList.set(i + 1, "-1");
                    tokenList.add(i + 2, "*");
                }
                switch (tokenList.get(i)) {
                        case "(":
                            stack.push("(");
                            break;

                        case ")":
                            while (!stack.peek().equals("("))
                                convertedString.add(stack.pop());
                            stack.pop();
                            break;

                        case "*":
                        case "/":
                            while (!stack.empty() && (stack.peek().equals("*") || stack.peek().equals("/")))
                                convertedString.add(stack.pop());
                            stack.push(tokenList.get(i));
                            break;

                        case "+":
                        case "-":
                            while (!stack.empty() && !stack.peek().equals("("))
                                convertedString.add(stack.pop());
                            stack.push(tokenList.get(i));
                            break;

                        default:
                            break;
                }
        }

        while (!stack.empty())
            convertedString.add(stack.pop());
        return convertedString;
    }
    int calculate(String mathString) {
        List<String> converted = convertToRPN(mathString);
        Stack<Integer> numStack = new Stack<>();
        int secondOperand;
        for (int i = 0, n = converted.size(); i < n; i++) {
            switch (converted.get(i)) {
                case "*":
                    numStack.push(numStack.pop() * numStack.pop());
                    break;
                case "/":
                    secondOperand = numStack.pop();
                    numStack.push(numStack.pop() / secondOperand);
                    break;
                case "+":
                    numStack.push(numStack.pop() + numStack.pop());
                    break;
                case "-":
                    secondOperand = numStack.pop();
                    numStack.push(numStack.pop() - secondOperand);
                    break;
                default:
                    numStack.push(Integer.valueOf(converted.get(i)));
                    break;
            }
        }
        return numStack.pop();
    }
    public static void main(String[] args) {
        ReversePolishNotation example = new ReversePolishNotation();
        int result = example.calculate("-(72/12-8*-(-4)+7)");
        System.out.println(result);
    }
}
