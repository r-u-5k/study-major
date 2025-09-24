package yujin;

public class MyQueue {
    private MyArrayList list;
    private int capacity;  // 최대 저장 용량

    public MyQueue(int capacity) {
        list = new MyArrayList();
        this.capacity = capacity;
    }

    public void enqueue(Object e) {
        if (list.size() >= capacity) {
            throw new RuntimeException("큐가 가득 찼습니다.");
        }
        list.add(list.size(), e);
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("큐가 비어 있습니다.");
        }
        return list.remove(0);
    }

    public Object front() {
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue(100);

        for (int i = 1; i <= 10; i++) {
            queue.enqueue(i);
        }

        while (!queue.isEmpty()) {
            System.out.printf(queue.dequeue() + " ");
        }
    }
}
