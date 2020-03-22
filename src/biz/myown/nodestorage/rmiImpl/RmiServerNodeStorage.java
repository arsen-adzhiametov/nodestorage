package biz.myown.nodestorage.rmiimpl;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.impl.NodeStorageFactory;
import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.storageinterface.NodeStorage;
import biz.myown.nodestorage.storageinterface.RmiNodeStorage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServerNodeStorage implements NodeStorage {

    private RmiNodeStorage server;

    public RmiServerNodeStorage(NodeStorage nodeStorage, String name, int port) throws RemoteException, NodeStorageException {
        server = new RmiNodeStorageServer(nodeStorage);
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(name, server);
            System.out.println("Remote server running at port: " + port + "\n" + "whith " + nodeStorage.getClass().getName());
        } catch (RemoteException e) {
            throw new NodeStorageException("RMI server failed to start", e);
        }
    }

    @Override
    public Node read() throws NodeStorageException {
        Node node = null;
        try {
            node = server.read();
        } catch (RemoteException e) {
            throw new NodeStorageException("RMI server failed to read node", e);
        }
        return node;
    }

    @Override
    public void store(Node node) throws NodeStorageException {
        try {
            server.store(node);
        } catch (RemoteException e) {
            throw new NodeStorageException("RMI server failed to store node", e);
        }
    }

    public static void main(String args[]) throws NodeStorageException {
        NodeStorage nodeStorage = NodeStorageFactory.getNodeStorage(NodeStorageFactory.StorageType.XML_DOM);

        try {
            NodeStorage server = new RmiServerNodeStorage(nodeStorage, "rmiServer", 3030);
        } catch (RemoteException e) {
            throw new NodeStorageException(e);
        } catch (NodeStorageException e) {
            throw e;
        }

    }
}
