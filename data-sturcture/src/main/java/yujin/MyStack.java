package yujin;

public class MyStack {
    private MyArrayList list;
    private int capacity;

    public MyStack(int capacity) {
        list = new MyArrayList();
        this.capacity = capacity;
    }

    public void push(Object e) {
        if (list.size() >= capacity) {
            throw new RuntimeException("스택이 가득 찼습니다.");
        }
        list.add(list.size(), e);
    }

    public Object pop() {
        if (isEmpty()) {
            throw new RuntimeException("스택이 비어 있습니다.");
        }
        return list.remove(list.size() - 1);
    }

    public Object top() {
        return list.get(list.size() - 1);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack(100);

        for (int i = 1; i <= 10; i++) {
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            System.out.printf(stack.pop() + " ");
        }
    }
}
