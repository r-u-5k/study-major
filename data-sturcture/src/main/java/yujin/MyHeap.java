package yujin;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyHeap {
    private final ArrayList<MyNode> list = new ArrayList<>();
    private final boolean isMinHeap;

    /**
     * @param isMinHeap true면 최소 힙, false면 최대 힙
     */
    public MyHeap(boolean isMinHeap) {
        this.isMinHeap = isMinHeap;
    }

    /** 새 노드를 힙에 삽입 */
    public void offer(MyNode node) {
        list.add(node);
        heapifyUp(list.size() - 1);
    }

    /** 루트 노드를 제거하고 반환 */
    public MyNode poll() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        MyNode root = list.get(0);
        MyNode last = list.remove(list.size() - 1);
        if (!list.isEmpty()) {
            list.set(0, last);
            heapifyDown(0);
        }
        return root;
    }

    /** 루트 노드를 조회만 (삭제 안 함) */
    public MyNode peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    /** 새로 삽입된 노드를 위로 올려 힙 속성 유지 */
    private void heapifyUp(int idx) {
        MyNode curr = list.get(idx);
        while (idx > 0) {
            int parentIdx = (idx - 1) / 2;
            MyNode parent = list.get(parentIdx);
            if (compare(curr, parent) >= 0) break;

            list.set(idx, parent);
            list.set(parentIdx, curr);
            idx = parentIdx;
        }
    }

    /** 루트로 옮겨진 노드를 아래로 내려 힙 속성 유지 */
    private void heapifyDown(int idx) {
        int size = list.size();
        MyNode curr = list.get(idx);

        while (true) {
            int leftIdx  = 2 * idx + 1;
            int rightIdx = 2 * idx + 2;
            if (leftIdx >= size) break;  // 자식이 없으면 끝

            // 왼쪽 자식을 기본으로
            int childIdx = leftIdx;

            // 오른쪽 자식이 있고, 왼쪽보다 우선순위가 더 높으면(=작거나/크거나)
            if (rightIdx < size && compare(list.get(rightIdx), list.get(leftIdx)) < 0) {
                childIdx = rightIdx;
            }

            // 자식과 비교해서 교환 필요 없으면 종료
            if (compare(list.get(childIdx), curr) >= 0) break;

            // 교환
            list.set(idx, list.get(childIdx));
            list.set(childIdx, curr);
            idx = childIdx;
        }
    }

    /**
     * a와 b를 비교하여,
     *  - 최소 힙이면 a.element < b.element → 음수 반환
     *  - 최대 힙이면 a.element > b.element → 음수 반환
     */
    private int compare(MyNode a, MyNode b) {
        Object ea = a.element();
        Object eb = b.element();
        if (!(ea instanceof Comparable) || !(eb instanceof Comparable)) {
            throw new ClassCastException("MyNode.element must implement Comparable");
        }
        int cmp = ((Comparable<Object>) ea).compareTo(eb);
        return isMinHeap ? cmp : -cmp;
    }
}
