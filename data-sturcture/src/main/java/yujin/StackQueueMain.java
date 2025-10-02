package yujin;

public class StackQueueMain {
    public static void main(String[] args) {
        StackQueue queue = new StackQueue(100);

        // 1) 'a' ~ 'e'까지 enqueue
        for (char c = 'a'; c <= 'e'; c++) {
            queue.enqueue(c);
        }

        // 2) 3개 dequeue
        for (int i = 0; i < 3; i++) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();

        // 3) 'f' ~ 'j'까지 enqueue
        for (char c = 'f'; c <= 'j'; c++) {
            queue.enqueue(c);
        }

        // 4) 3개 dequeue
        for (int i = 0; i < 3; i++) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();

        // 5) 'k' ~ 'o'까지 enqueue
        for (char c = 'k'; c <= 'o'; c++) {
            queue.enqueue(c);
        }

        // 6) 6개 dequeue
        for (int i = 0; i < 6; i++) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();

        // 7) 남은 문자들 dequeue (순서대로 출력)
        while (!queue.isEmpty()) {
            System.out.print(queue.dequeue() + " ");
        }
    }
}

