package yujin;

public class MyBinNode extends MyNode {
    private MyBinNode left;
    private MyBinNode right;

    MyBinNode() {
        super();
        left = null;
        right = null;
    }

    MyBinNode(Object e) {
        super(e);
        left = null;
        right = null;
    }

    public MyBinNode left() {
        return left;
    }

    public MyBinNode right() {
        return right;
    }

    public void setLeft(MyBinNode v) {
        left = v;
        if (v != null) {
            v.setParent(this);
        }
    }

    public void setRight(MyBinNode v) {
        right = v;
        if (v != null) {
            v.setParent(this);
        }
    }

}
