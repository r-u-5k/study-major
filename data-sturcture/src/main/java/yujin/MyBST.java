package yujin;

import java.util.ArrayList;
import java.util.Stack;

public class MyBST extends MyBinTree {

    public MyBST() {
        super();
    }

    public MyBST(Object e) {
        super(e);
    }

    private MyBinNode nextNode(MyBinNode v) {
        if (v == null) {
            return null;
        }

        if (hasRight(v)) {
            MyBinNode w = v.right();
            while (w.left() != null) {
                w = w.left();
            }
            return w;
        }

        MyBinNode parent = parent(v);
        MyBinNode curr = v;
        while (parent != null && parent.right() == curr) {
            curr = parent;
            parent = parent(parent);
        }
        return parent;
    }

    public Object find(Object k) {
        if (k == null) {
            return null;
        }
        Integer key = (Integer) k;
        MyBinNode curr = root();

        while (curr != null) {
            Integer currKey = (Integer) curr.element();
            if (key.equals(currKey)) {
                return currKey;
            }
            if (key < currKey) {
                curr = curr.left();
            } else {
                curr = curr.right();
            }
        }
        return null;
    }

    public ArrayList findAll(Object k) {
        ArrayList<Integer> result = new ArrayList<>();
        if (k == null) {
            return result;
        }
        Integer key = (Integer) k;

        Stack<MyBinNode> stack = new Stack<>();
        MyBinNode curr = root();
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left();
            }
            curr = stack.pop();
            Integer currKey = (Integer) curr.element();
            if (key.equals(currKey)) {
                result.add(currKey);
            }
            curr = curr.right();
        }

        return result;
    }

    public Object insert(Object k) {
        if (k == null) {
            return null;
        }
        Integer key = (Integer) k;

        if (isEmpty()) {
            MyBinNode newRoot = addRoot(key);
            return newRoot.element();
        }

        MyBinNode curr = root();
        MyBinNode parent = null;

        while (curr != null) {
            parent = curr;
            Integer currKey = (Integer) curr.element();
            if (key < currKey) {
                curr = curr.left();
            } else {
                curr = curr.right();
            }
        }

        MyBinNode newNode = new MyBinNode(key);
        newNode.setParent(parent);

        Integer parentKey = (Integer) parent.element();
        if (key < parentKey) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        return newNode.element();
    }

    public Object remove(Object k) {
        if (k == null) {
            return null;
        }
        Integer key = (Integer) k;

        MyBinNode curr = root();
        MyBinNode nodeToRemove = null;
        while (curr != null) {
            Integer currKey = (Integer) curr.element();
            if (key.equals(currKey)) {
                nodeToRemove = curr;
                break;
            }
            if (key < currKey) {
                curr = curr.left();
            } else {
                curr = curr.right();
            }
        }
        if (nodeToRemove == null) {
            return null;
        }

        if (hasLeft(nodeToRemove) && hasRight(nodeToRemove)) {
            MyBinNode succ = nextNode(nodeToRemove);
            nodeToRemove.setElement(succ.element());
            nodeToRemove = succ;
        }

        try {
            super.remove(nodeToRemove);
        } catch (TwoChildrenException e) {
            e.printStackTrace();
        }
        return key;
    }


}
