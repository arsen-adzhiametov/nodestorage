package biz.myown.nodestorage;

import biz.myown.nodestorage.node.Node;

public class NodesStructure {

    private static Node root;

    static {

        //complex node structure
        root = new Node();
        root.setData("root");

        Node child0 = new Node();
        child0.setData("child0 of root");

        root.getChildNodes().add(child0);

        Node child1 = new Node();
        child1.setData("child1 of root");

        root.getChildNodes().add(child1);

        Node child2 = new Node();
        child2.setData("child2 of child0");

        child0.getChildNodes().add(child2);

        Node child3 = new Node();
        child3.setData("child3 of child0");

        child0.getChildNodes().add(child3);
    }

    public static Node getNode() {
        return root;
    }
}
