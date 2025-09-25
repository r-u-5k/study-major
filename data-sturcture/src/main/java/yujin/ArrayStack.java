package yujin;

public class ArrayStack implements Stack {
    private char[] stack;
    private int top;

    public ArrayStack(int capacity) {
        stack = new char[capacity];
        top = -1;
    }

    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public char top() {
        if (isEmpty()) {
            throw new IllegalStateException("스택이 비어 있습니다.");
        }
        return stack[top];
    }

    @Override
    public char push(char o) {
        if (top == stack.length - 1) {
            throw new IllegalStateException("스택이 가득 찼습니다.");
        }
        stack[++top] = o;
        return o;
    }

    @Override
    public char pop() {
        if (isEmpty()) {
            throw new IllegalStateException("스택이 비어 있습니다.");
        }
        return stack[top--];
    }
}