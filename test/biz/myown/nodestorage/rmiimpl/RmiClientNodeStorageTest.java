package biz.myown.nodestorage.rmiimpl;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.impl.NodeStorageFactory;
import biz.myown.nodestorage.impl.NodesStructure;
import biz.myown.nodestorage.storageinterface.NodeStorage;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

public class RmiClientNodeStorageTest {
    NodeStorage nodeStorage = NodeStorageFactory.getNodeStorage(NodeStorageFactory.StorageType.XML_JAXB);
    NodeStorage server;

    public RmiClientNodeStorageTest() throws NodeStorageException, RemoteException {
        server = new RmiServerNodeStorage(nodeStorage, "rmiServer", 3030);
    }


    @Test
    public void testReadAndStore() throws Exception {
        NodeStorage client = new RmiClientNodeStorage("rmiServer", 3030);
        client.store(NodesStructure.getNode());
        assertEquals(client.read(), NodesStructure.getNode());
    }

}
