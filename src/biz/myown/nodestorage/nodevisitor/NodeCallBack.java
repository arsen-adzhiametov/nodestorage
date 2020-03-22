package biz.myown.nodestorage.nodevisitor;

import biz.myown.nodestorage.node.Node;

public interface NodeCallBack {
    Object before(Node node, Object parentNode) throws Exception;
    void after(Node node) throws Exception;
}
