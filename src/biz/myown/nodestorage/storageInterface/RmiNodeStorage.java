package biz.myown.nodestorage.storageinterface;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.node.Node;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiNodeStorage extends Remote {

    public Node read() throws NodeStorageException, RemoteException;

    public void store(Node node) throws NodeStorageException, RemoteException;

}