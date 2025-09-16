package yujin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Tree1 {
    public static void main(String[] args) {
        MyTree tree = new MyTree();
        MyNode root = tree.addRoot("Computers'R Us");
        MyNode sales = tree.addChild(root, "Sales");
        MyNode manufacturing = tree.addChild(root, "Manufacturing");
        MyNode rnd = tree.addChild(root, "R&D");
        MyNode us = tree.addChild(sales, "US");
        MyNode node5 = tree.addChild(sales, "International");
        MyNode laptops = tree.addChild(manufacturing, "Laptops");
        MyNode desktops = tree.addChild(manufacturing, "Desktops");
        MyNode europe = tree.addChild(node5, "Europe");
        MyNode asia = tree.addChild(node5, "Asia");
        MyNode canada = tree.addChild(node5, "Canada");

        printTree(root);
        System.out.printf("* Tree size = Total %d Nodes", tree.size());

    }

    public static void printTree(MyNode root) {
        if (root == null) return;

        Queue<MyNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            System.out.println("[Level " + level + "]");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                MyNode node = queue.poll();
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(node.element());
                ArrayList<MyNode> children = node.children();
                for (int j = 0; j < children.size(); j++) {
                    queue.add(children.get(j));
                }
            }
            System.out.println(sb);
            System.out.println();
            level++;
        }
    }
}
