package biz.myown.nodestorage.rmiimpl;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.storageinterface.NodeStorage;
import biz.myown.nodestorage.storageinterface.RmiNodeStorage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClientNodeStorage implements NodeStorage {

    private String rmiUrl;
    private int port;

    public RmiClientNodeStorage(String rmiUrl, int port) {
        this.rmiUrl = rmiUrl;
        this.port = port;
    }

    @Override
    public Node read() throws NodeStorageException {
        Node node = null;
        try {
            node = getServer().read();
        } catch (RemoteException e) {
            throw new NodeStorageException("RMI client failed to read node", e);
        }
        return node;
    }

    @Override
    public void store(Node node) throws NodeStorageException {
        try {
            getServer().store(node);
        } catch (RemoteException e) {
            throw new NodeStorageException("RMI client failed to store node", e);
        }
    }

    private RmiNodeStorage getServer() throws NodeStorageException {
        RmiNodeStorage rmiServer = null;
        Registry registry;

        try {
            registry = LocateRegistry.getRegistry(port);
            rmiServer = (RmiNodeStorage) (registry.lookup(rmiUrl));

        } catch (RemoteException e) {
            throw new NodeStorageException("Failed to get RMI server", e);
        } catch (NotBoundException e) {
            throw new NodeStorageException("Failed to get RMI server", e);
        }
        return rmiServer;
    }

    public static void main(String args[]) throws NodeStorageException {
        NodeStorage client = new RmiClientNodeStorage("rmiServer", 3030);
        try {
            System.out.println(client.read());
        } catch (NodeStorageException e) {
            throw e;
        }
    }
}
