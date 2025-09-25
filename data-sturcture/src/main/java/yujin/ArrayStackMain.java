package yujin;

import java.util.Scanner;

public class ArrayStackMain {
    public static String invert(String expr) {
        ArrayStack stack = new ArrayStack(expr.length());

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') {
                stack.push(')');
            } else if (c == ')') {
                stack.push('(');
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("입력: ");
        String expr = sc.nextLine();
        String inverted = invert(expr);
        System.out.println("출력: " + inverted);
        sc.close();
    }
}
