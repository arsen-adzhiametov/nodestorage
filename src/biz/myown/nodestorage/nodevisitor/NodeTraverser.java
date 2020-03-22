package biz.myown.nodestorage.nodevisitor;

import biz.myown.nodestorage.node.Node;

public class NodeTraverser {

    public static void traverseNode(Node node, NodeCallBack callback, Object parentNode) throws Exception {
        parentNode = callback.before(node, parentNode);
        for (int i = 0; i < node.getChildNodes().size(); i++){
            traverseNode(node.getChildNodes().get(i), callback, parentNode);
        }
    }
}
