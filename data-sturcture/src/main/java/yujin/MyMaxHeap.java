package yujin;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyMaxHeap {
    ArrayList<MyNode> list = new ArrayList<>();

    /** 새 노드를 힙에 삽입하고, 바로 위로 올려서 힙 속성 복원 */
    public void offer(MyNode node) {
        list.add(node); // 1) 맨 끝에 추가
        int idx = list.size() - 1; // 2) 추가된 위치

        // 3) 부모와 비교하며 위로 교환
        while (idx > 0) {
            int parentIdx = (idx - 1) / 2;
            if (compare(list.get(idx), list.get(parentIdx)) <= 0) {
                break; // 부모 >= 현재면 중단
            }
            // swap
            MyNode tmp = list.get(parentIdx);
            list.set(parentIdx, list.get(idx));
            list.set(idx, tmp);

            idx = parentIdx; // 검사 위치를 부모로 이동
        }
    }

    /** 루트 노드를 제거하고, 마지막 노드를 루트로 채운 뒤 아래로 내려 힙 속성 복원 */
    public MyNode poll() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        MyNode root = list.get(0); // 1) 최댓값 저장
        MyNode last = list.remove(list.size() - 1); // 2) 마지막 노드 꺼내기

        if (!list.isEmpty()) {
            list.set(0, last); // 3) 루트에 마지막 노드 배치

            int idx = 0;
            int size = list.size();

            // 4) 자식과 비교하며 아래로 교환
            while (true) {
                int leftIdx  = 2 * idx + 1;
                int rightIdx = 2 * idx + 2;

                if (leftIdx >= size) break; // 자식이 없으면 종료

                // 둘 중 더 큰(우선순위 높은) 자식 선택
                int childIdx = leftIdx;
                if (rightIdx < size
                        && compare(list.get(rightIdx), list.get(leftIdx)) > 0) {
                    childIdx = rightIdx;
                }

                if (compare(list.get(childIdx), list.get(idx)) <= 0) {
                    break; // 현재 >= 자식이면 종료
                }

                // swap
                MyNode tmp = list.get(idx);
                list.set(idx, list.get(childIdx));
                list.set(childIdx, tmp);

                idx = childIdx; // 검사 위치를 자식으로 이동
            }
        }

        return root; // 5) 원래 루트 반환
    }

    /** 힙에 저장된 노드 수 */
    public int size() {
        return list.size();
    }

    /**
     * @return a > b 이면 양수, a < b 이면 음수, 같으면 0
     */
    private int compare(MyNode a, MyNode b) {
        return ((Comparable<Object>)a.element()).compareTo(b.element());
    }
}
