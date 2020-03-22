package biz.myown.nodestorage.impl;

import biz.myown.nodestorage.storageinterface.NodeStorage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlNodeStorageTest {

    @Test
    public void testWrite() throws Exception {
        NodeStorage nodeStorage = NodeStorageFactory.getNodeStorage(NodeStorageFactory.StorageType.SQL_JDBC);
        nodeStorage.store(NodesStructure.getNode());
    }

    @Test
    public void testRead() throws Exception {
        NodeStorage nodeStorage = NodeStorageFactory.getNodeStorage(NodeStorageFactory.StorageType.SQL_JDBC);
        assertEquals(nodeStorage.read(), NodesStructure.getNode());
    }

}
