package yujin;

public class StackQueue implements Queue {
    ArrayStack inStack;
    ArrayStack outStack;

    public StackQueue() {
        inStack = new ArrayStack(100);
        outStack = new ArrayStack(100);
    }

    public StackQueue(int capacity) {
        inStack = new ArrayStack(capacity);
        outStack = new ArrayStack(capacity);
    }

    @Override
    public int size() {
        return inStack.size() + outStack.size();
    }

    @Override
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    @Override
    public char front() {
        if (isEmpty()) {
            throw new IllegalStateException("큐가 비어 있습니다.");
        }
        if (outStack.isEmpty()) {
            moveInToOut();
        }
        return outStack.top();
    }

    @Override
    public void enqueue(char o) {
        inStack.push(o);
    }

    @Override
    public char dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("큐가 비어 있습니다.");
        }
        if (outStack.isEmpty()) {
            moveInToOut();
        }
        return outStack.pop();
    }

    // inStack에서 outStack으로 이동
    public void moveInToOut() {
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
    }
}
