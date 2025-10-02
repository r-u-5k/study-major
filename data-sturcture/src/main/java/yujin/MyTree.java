package yujin;

import java.util.ArrayList;

public class MyTree {
    MyNode root;

    MyTree() {
        root = null;
    }

    MyTree(Object e) {
        root = new MyNode(e);
        root.setChildren(new ArrayList());
    }

    public int size() {
        if (root == null) return 0;

        int count = 0;
        ArrayList<MyNode> queue = new ArrayList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            MyNode current = queue.remove(0);
            count++;

            if (current.children() != null) {
                queue.addAll(current.children());
            }
        }

        return count;
    }

    public MyNode root() {
        return root;
    }

    public ArrayList children(MyNode v) {
        return v.children();
    }

    public boolean isExternal(MyNode v) {
        return v.children().isEmpty();
    }

    public MyNode addRoot(Object e) {
        root = new MyNode(e);
        root.setChildren(new ArrayList());
        return root;
    }

    public MyNode addNode(Object e) {
        MyNode newNode = new MyNode(e);
        newNode.setChildren(new ArrayList());
        return newNode;
    }

    public MyNode addChild(MyNode v, Object e) {
        MyNode child = new MyNode(e);
        child.setChildren(new ArrayList());
        child.setParent(v);
        v.children().add(child);
        return child;
    }

    public MyNode addChild(MyNode v, int i, Object e) {
        MyNode child = new MyNode(e);
        child.setChildren(new ArrayList());
        child.setParent(v);
        v.children().add(i, child);
        return child;
    }

    public MyNode setChild(MyNode v, int i, Object e) {
        ArrayList<MyNode> children = v.children();
        MyNode oldChild = children.get(i);
        MyNode newChild = new MyNode(e);
        newChild.setChildren(new ArrayList());
        newChild.setParent(v);
        children.set(i, newChild);
        oldChild.setParent(null);
        return oldChild;
    }

    public MyNode removeChild(MyNode v, int i) {
        ArrayList<MyNode> children = v.children();
        MyNode removedChild = children.remove(i);
        removedChild.setParent(null);
        return removedChild;
    }
}
