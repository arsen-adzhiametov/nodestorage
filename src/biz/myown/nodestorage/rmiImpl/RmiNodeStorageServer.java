package biz.myown.nodestorage.rmiimpl;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.storageinterface.NodeStorage;
import biz.myown.nodestorage.storageinterface.RmiNodeStorage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiNodeStorageServer extends UnicastRemoteObject implements RmiNodeStorage {

    private NodeStorage nodeStorage;

    public RmiNodeStorageServer(NodeStorage nodeStorage) throws RemoteException {
        this.nodeStorage = nodeStorage;
    }

    @Override
    public Node read() throws NodeStorageException, RemoteException{
        Node node = nodeStorage.read();
        return node;
    }

    @Override
    public void store(Node node) throws NodeStorageException, RemoteException {
        nodeStorage.store(node);
    }

}
