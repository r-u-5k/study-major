package yujin;

public class MyBinTree extends MyTree {
    public MyBinTree() {
        super();
    }

    public MyBinTree(Object e) {
        super(e);
    }

    public Boolean isEmpty() {
        return (super.root() == null);
    }

    public boolean isRoot(MyBinNode v) {
        return (v == root());
    }

    public boolean isInternal(MyBinNode v) {
        return (v.left() != null || v.right() != null);
    }

    public boolean isExternal(MyBinNode v) {
        return (v.left() == null && v.right() == null);
    }

    public MyBinNode root() {
        return (MyBinNode) super.root();
    }

    public MyBinNode parent(MyBinNode v) {
        return (MyBinNode) v.parent();
    }

    public MyBinNode left(MyBinNode v) {
        return v.left();
    }

    public MyBinNode right(MyBinNode v) {
        return v.right();
    }

    public boolean hasLeft(MyBinNode v) {
        return (v.left() != null);
    }

    public boolean hasRight(MyBinNode v) {
        return (v.right() != null);
    }

    public MyBinNode addRoot(Object e) {
        MyBinNode newRoot = new MyBinNode(e);
        this.root = newRoot;
        return newRoot;
    }

    public MyBinNode addNode(Object e) {
        return new MyBinNode(e);
    }

    public MyBinNode insertLeft(MyBinNode v, Object e) {
        MyBinNode newNode = new MyBinNode(e);
        v.setLeft(newNode);
        return newNode;
    }

    public MyBinNode insertRight(MyBinNode v, Object e) {
        MyBinNode newNode = new MyBinNode(e);
        v.setRight(newNode);
        return newNode;
    }

    public Object replace(MyBinNode v, Object e) {
        Object old = v.element();
        v.setElement(e);
        return old;
    }

    public MyBinNode remove(MyBinNode v) throws TwoChildrenException {
        if (hasLeft(v) && hasRight(v)) {
            throw new TwoChildrenException();
        }
        MyBinNode child = (v.left() != null) ? v.left() : v.right();
        if (isRoot(v)) {
            this.root = child;
            if (child != null) {
                child.setParent(null);
            }
        } else {
            MyBinNode p = parent(v);
            if (p.left() == v) {
                p.setLeft(child);
            } else if (p.right() == v) {
                p.setRight(child);
            }
            if (child != null) {
                child.setParent(p);
            }
        }
        return v;
    }

    public void attach(MyBinNode v, MyBinNode t1, MyBinNode t2)
            throws NotExternalException {
        if (!isExternal(v)) {
            throw new NotExternalException();
        }
        v.setLeft(t1);
        v.setRight(t2);
    }

    public static class NotExternalException extends Exception {
        public NotExternalException() {
            super();
        }
    }

    public static class TwoChildrenException extends Exception {
        public TwoChildrenException() {
            super();
        }
    }

}
