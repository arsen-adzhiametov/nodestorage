package biz.myown.nodestorage.exception;

public class NodeStorageException extends Exception {

    public NodeStorageException() {
    }

    public NodeStorageException(String message) {
        super(message);
    }

    public NodeStorageException(Exception e) {
        super(e);
    }

    public NodeStorageException(String message, Exception e) {
        super(message, e);
    }

}
