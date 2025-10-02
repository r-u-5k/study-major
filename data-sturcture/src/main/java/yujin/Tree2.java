package yujin;

import java.util.ArrayList;

public class Tree2 {
    public static void main(String[] args) {
        MyTree tree = new MyTree();
        MyNode root = tree.addRoot("Make Money Fast!");
        MyNode motivations = tree.addChild(root, "1. Motivations");
        MyNode methods = tree.addChild(root, "2. Methods");
        MyNode references = tree.addChild(root, "References");
        MyNode greed = tree.addChild(motivations, "1.1 Greed");
        MyNode avidity = tree.addChild(motivations, "1.2 Avidity");
        MyNode stock = tree.addChild(methods, "2.1 Stock Fraud");
        MyNode ponzi = tree.addChild(methods, "2.2 Ponzi Scheme");
        MyNode bank = tree.addChild(methods, "2.3 Bank Robbery");

        preorderPrint(root, 0);

    }

    public static void preorderPrint(MyNode node, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(node.element());

        ArrayList<MyNode> children = node.children();
        for (int i = 0; i < children.size(); i++) {
            preorderPrint(children.get(i), level + 1);
        }
    }

}
