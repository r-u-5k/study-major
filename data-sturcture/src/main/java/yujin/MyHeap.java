package yujin;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyHeap {
    private final ArrayList<MyNode> list = new ArrayList<>(); // 힙 요소를 저장할 내부 배열
    private final boolean isMinHeap;                          // 최소 힙인지 여부 표시

    /**
     * @param isMinHeap true면 최소 힙, false면 최대 힙
     */
    public MyHeap(boolean isMinHeap) {
        this.isMinHeap = isMinHeap; // 생성 시 최소/최대 힙 설정
    }

    /**
     * 새 노드를 힙에 삽입
     */
    public void offer(MyNode node) {
        list.add(node);                   // 배열 끝에 노드 추가
        heapifyUp(list.size() - 1);  // 삽입된 노드를 위로 올려 힙 속성 유지
    }

    /**
     * 루트 노드를 제거하고 반환
     */
    public MyNode poll() {
        if (list.isEmpty()) {            // 힙이 비어있다면
            throw new NoSuchElementException(); // 예외 발생
        }
        MyNode root = list.get(0);       // 루트 노드 저장
        MyNode last = list.remove(list.size() - 1); // 마지막 노드 제거 후 저장
        if (!list.isEmpty()) {           // 남은 요소가 있으면
            list.set(0, last);           // 루트에 마지막 노드 배치
            heapifyDown(0);         // 루트 노드를 아래로 내려 힙 속성 유지
        }
        return root;                     // 제거된 원래 루트 반환
    }

    /**
     * 루트 노드를 삭제 없이 조회만 함
     */
    public MyNode peek() {
        if (list.isEmpty()) {            // 힙이 비어있다면
            throw new NoSuchElementException(); // 예외 발생
        }
        return list.get(0);              // 루트 노드 반환
    }

    /**
     * 힙에 저장된 요소 개수 반환
     */
    public int size() {
        return list.size();              // 내부 배열의 크기 반환
    }

    /**
     * 삽입된 노드를 위로 올려 힙 속성(부모와 비교하여 올바른 위치) 유지
     */
    private void heapifyUp(int idx) {
        MyNode curr = list.get(idx);     // 현재 노드 저장
        while (idx > 0) {                // 루트가 아닐 동안 반복
            int parentIdx = (idx - 1) / 2; // 부모 노드 인덱스 계산
            MyNode parent = list.get(parentIdx); // 부모 노드 가져오기
            if (compare(curr, parent) >= 0)
                break; // 이미 올바른 위치이면 종료

            list.set(idx, parent);       // 부모와 위치 교환
            list.set(parentIdx, curr);   // 현재 노드를 부모 위치로 이동
            idx = parentIdx;             // 인덱스를 부모 인덱스로 업데이트
        }
    }

    /**
     * 루트로 온 노드를 아래로 내려 힙 속성을 유지
     */
    private void heapifyDown(int idx) {
        int size = list.size();          // 힙 크기 저장
        MyNode curr = list.get(idx);     // 현재 노드 저장

        while (true) {
            int leftIdx = 2 * idx + 1;   // 왼쪽 자식 인덱스
            int rightIdx = 2 * idx + 2;  // 오른쪽 자식 인덱스
            if (leftIdx >= size) break;  // 자식이 없으면 종료

            int childIdx = leftIdx;      // 기본 선택: 왼쪽 자식

            // 오른쪽 자식이 있고, 우선순위가 더 높다면(=작거나/크거나)
            if (rightIdx < size && compare(list.get(rightIdx), list.get(leftIdx)) < 0) {
                childIdx = rightIdx;     // 오른쪽 자식을 선택
            }

            if (compare(list.get(childIdx), curr) >= 0) break; // 교환 불필요 시 종료

            // 현재 노드와 자식 노드 교환
            list.set(idx, list.get(childIdx));
            list.set(childIdx, curr);
            idx = childIdx;              // 인덱스를 교환된 자식 위치로 업데이트
        }
    }

    /**
     * 두 노드 a, b의 element 값을 비교
     * - 최소 힙이면 a.element < b.element → 음수 반환
     * - 최대 힙이면 위 결과를 반전하여 반환
     */
    private int compare(MyNode a, MyNode b) {
        Object ea = a.element();        // a의 element 추출
        Object eb = b.element();        // b의 element 추출
        int cmp = ((Comparable<Object>) ea).compareTo(eb); // Comparable 캐스팅 후 비교
        return isMinHeap ? cmp : -cmp;  // 최소/최대 힙에 따라 부호 조정
    }
}
