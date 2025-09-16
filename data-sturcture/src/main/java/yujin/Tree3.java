package yujin;

import java.util.ArrayList;

public class Tree3 {
    public static void main(String[] args) {
        MyTree tree = new MyTree();
        MyNode cs16 = tree.addRoot("cs16/");
        MyNode homeworks = tree.addChild(cs16, "homeworks/");
        MyNode programs = tree.addChild(cs16, "programs/");
        MyNode todo = tree.addChild(cs16, "todo.txt (1K)");
        MyNode h1c = tree.addChild(homeworks, "h1c.doc (3K)");
        MyNode h1nc = tree.addChild(homeworks, "h1nc.doc (2K)");
        MyNode ddr = tree.addChild(programs, "DDR.java (10K)");
        MyNode stocks = tree.addChild(programs, "Stocks.java (25K)");
        MyNode robot = tree.addChild(programs, "Robot.java (20K)");

        postorderPrint(cs16);
    }

    public static int postorderPrint(MyNode node) {
        int sumSize = 0;
        ArrayList<MyNode> children = node.children();
        for (int i = 0; i < children.size(); i++) {
            MyNode child = children.get(i);
            sumSize += postorderPrint(child);
        }

        String sizeStr = node.element().toString();
        int start = sizeStr.indexOf("(");
        int end = sizeStr.indexOf(")");
        if (start != -1 && end != -1) {
            String sizePart = sizeStr.substring(start + 1, end);
            sizePart = sizePart.replace("K", "");
            int fileSize = Integer.parseInt(sizePart);
            sumSize += fileSize;
        }

        if (sizeStr.endsWith("/")) {
            System.out.println(node.element() + " = " + sumSize + "KB");
        }
        return sumSize;
    }

}
