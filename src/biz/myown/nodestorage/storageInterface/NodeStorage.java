package biz.myown.nodestorage.storageinterface;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.node.Node;

public interface NodeStorage {

    public Node read() throws NodeStorageException;

    public void store(Node node) throws NodeStorageException;

}
