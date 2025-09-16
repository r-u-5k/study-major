package yujin;

public class BinaryTree {
    public static void main(String[] args) {
        MyBinTree bt = new MyBinTree();
        MyBinNode root = bt.addRoot("+");

        MyBinNode leftSubtree = bt.insertLeft(root, "*");
        bt.insertLeft(leftSubtree, "2");
        MyBinNode minusNode = bt.insertRight(leftSubtree, "-");
        bt.insertLeft(minusNode, "3");
        bt.insertRight(minusNode, "1");

        MyBinNode rightSubtree = bt.insertRight(root, "*");
        bt.insertLeft(rightSubtree, "3");
        bt.insertRight(rightSubtree, "2");

        String expression = inorder(root);
        int result = postorder(root);

        System.out.println(expression);
        System.out.println("= " + result);
    }

    public static String inorder(MyBinNode node) {
        if (node.left() == null && node.right() == null) {
            return node.element().toString();
        }
        String leftExpr = inorder(node.left());
        String rightExpr = inorder(node.right());
        return "(" + leftExpr + node.element().toString() + rightExpr + ")";
    }

    public static int postorder(MyBinNode node) {
        if (node.left() == null && node.right() == null) {
            return Integer.parseInt(node.element().toString());
        }
        int leftVal = postorder(node.left());
        int rightVal = postorder(node.right());
        String op = node.element().toString();
        switch (op) {
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "*":
                return leftVal * rightVal;
            case "/":
                return leftVal / rightVal;
            default:
                return 0;
        }
    }
}
