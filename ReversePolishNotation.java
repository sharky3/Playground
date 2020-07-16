import java.util.Stack;

public class ReversePolishNotation {
    private StringBuilder convertToRPN(String inputString) {
        StringBuilder convertedString = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        if(inputString.charAt(0) == '-') {
            convertedString.append('0');
            stack.push('-');
        }
        else {
            for (int i = 0, n = inputString.length(); i < n; i++) {
                char token = inputString.charAt(i);
                if (Character.isDigit(token)) {
                    convertedString.append(token);
                } else {
                    switch (token) {
                        case '(':
                            stack.push('(');
                            if(inputString.charAt(i+1) == '-') {
                                convertedString.append('0');
                                stack.push('-');
                                i++;
                            }
                            break;

                        case ')':
                            while(stack.peek() != '(')
                                convertedString.append(stack.pop());
                            stack.pop();
                            break;

                        case '*':
                        case '/':
                            if(!stack.empty() && (stack.peek() == '*' || stack.peek() == '/'))
                                convertedString.append(stack.pop());
                            stack.push(token);
                            break;

                        case '+':
                        case '-':
                            if(!stack.empty() && (stack.peek() != '('))
                                convertedString.append(stack.pop());
                            stack.push(token);
                            break;

                        default:
                            break;
                    }
                }
            }
        }
        while(!stack.empty())
            convertedString.append(stack.pop());
        return convertedString;
    }
    int calculate(String mathString) {
        StringBuilder converted = convertToRPN(mathString);
        Stack<Integer> numStack = new Stack<>();
        int secondOperand;
        for(int i = 0, n = converted.length(); i < n; i++){
            char token = converted.charAt(i);
            switch (token){
                case '*':
                    numStack.push(numStack.pop() * numStack.pop());
                    break;
                case '/':
                    secondOperand = numStack.pop();
                    numStack.push(numStack.pop() / secondOperand);
                    break;
                case '+':
                    numStack.push(numStack.pop() + numStack.pop());
                    break;
                case '-':
                    secondOperand = numStack.pop();
                    numStack.push(numStack.pop() - secondOperand);
                    break;
                default:
                    numStack.push(token - '0');
                    System.out.println(numStack);
                    break;
            }
        }
        return numStack.pop();
    }
    public static void main(String[] args){
        ReversePolishNotation example = new ReversePolishNotation();
        System.out.println(example.convertToRPN("2*(3+2*(1+2*(1+3)))"));
        System.out.println(example.calculate("2*(3+2*(1+2*(1+3)))"));
    }
}
