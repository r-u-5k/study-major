package yujin;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyMaxHeap {
    private final ArrayList<MyNode> list = new ArrayList<>();

    /** 새 노드를 힙에 삽입 */
    public void offer(MyNode node) {
        list.add(node);                    // 맨 끝(마지막 레벨의 오른쪽)에 노드를 추가
        heapifyUp(list.size() - 1);       // 추가된 노드를 위로 올려서 힙 속성을 복원
    }

    /** 루트 노드(가장 큰 값)를 제거하고 반환 */
    public MyNode poll() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
            // 비어있으면 예외 발생
        }
        MyNode root = list.get(0);         // 최댓값(루트)을 임시 저장
        MyNode last = list.remove(list.size() - 1);
        // 마지막 노드를 꺼내서

        if (!list.isEmpty()) {
            list.set(0, last);             // 꺼낸 마지막 노드를 루트에 배치
            heapifyDown(0);                // 아래로 내려서 힙 속성 복원
        }
        return root;                       // 원래 루트(최댓값)를 반환
    }

    /** 루트 노드를 삭제 없이 조회만 */
    public MyNode peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
            // 비어있으면 예외 발생
        }
        return list.get(0);                // 맨 위(최댓값) 노드를 반환
    }

    /** 현재 힙에 저장된 노드 개수 반환 */
    public int size() {
        return list.size();                // ArrayList의 크기를 그대로 반환
    }

    /** 삽입된 노드를 위로 올려서 힙 속성(부모 ≥ 자식)을 유지 */
    private void heapifyUp(int idx) {
        MyNode curr = list.get(idx);       // 현재 노드를 가져옴
        while (idx > 0) {
            int parentIdx = (idx - 1) / 2;  // 부모 인덱스 계산
            MyNode parent = list.get(parentIdx);

            if (compare(curr, parent) <= 0) break;
            // 현재 > 부모가 아니면(≤) 더 이상 교환할 필요 없음

            // swap: 부모와 자리를 바꿈
            list.set(idx, parent);
            list.set(parentIdx, curr);
            idx = parentIdx;               // 검사 위치를 부모로 이동
        }
    }

    /** 루트로 올라온 노드를 아래로 내려서 힙 속성(부모 ≥ 자식)을 유지 */
    private void heapifyDown(int idx) {
        int size = list.size();            // 현재 힙 크기
        MyNode curr = list.get(idx);       // 내려보낼 노드

        while (true) {
            int leftIdx  = 2 * idx + 1;    // 왼쪽 자식 인덱스
            int rightIdx = 2 * idx + 2;    // 오른쪽 자식 인덱스
            if (leftIdx >= size) break;    // 자식이 없으면 종료

            int childIdx = leftIdx;        // 일단 왼쪽을 후보로

            // 오른쪽 자식이 있고, 오른쪽이 왼쪽보다 크면(우선순위 높으면)
            if (rightIdx < size && compare(list.get(rightIdx), list.get(leftIdx)) > 0) {
                childIdx = rightIdx;       // 오른쪽을 선택
            }

            // 자식이 현재보다 크지 않으면(≤) 더 이상 내릴 필요 없음
            if (compare(list.get(childIdx), curr) <= 0) break;

            // swap: 자식과 현재 노드 자리 교환
            list.set(idx, list.get(childIdx));
            list.set(childIdx, curr);
            idx = childIdx;               // 검사 위치를 아래로 이동
        }
    }

    /**
     * 두 노드의 element() 값을 비교
     * @return a > b 이면 양수, a < b 이면 음수, 같으면 0
     */
    private int compare(MyNode a, MyNode b) {
        Object ea = a.element();
        Object eb = b.element();

        return ((Comparable<Object>) ea).compareTo(eb);
    }
}
