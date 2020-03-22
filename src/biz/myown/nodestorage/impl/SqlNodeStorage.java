package biz.myown.nodestorage.impl;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.storageinterface.NodeStorage;
import biz.myown.nodestorage.storagelogic.dao.NodeDAO;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlNodeStorage implements NodeStorage {

    private String serverName;
    private int portNumber;
    private String dbName;
    private String user;
    private String password;

    public SqlNodeStorage(String serverName, int portNumber, String dbName, String user, String password) {
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(serverName);
        ds.setPortNumber(portNumber);
        ds.setDatabaseName(dbName);
        ds.setUser(user);
        ds.setPassword(password);
        return ds.getConnection();
    }

    @Override
    public Node read() throws NodeStorageException {

//        DAOFactory daoFactory = new DAOFactory();
//        NodeDAO nodeDAO = daoFactory.getNodeDAO();

        NodeDAO nodeDAO = new NodeDAO(this);
        Node node = null;
        try {
            node = nodeDAO.findNode();
        } catch (SQLException e) {
            throw new NodeStorageException("SQL storage failed to read node", e);
        }
        return node;
    }

    @Override
    public void store(Node node) throws NodeStorageException {

//        DAOFactory daoFactory = new DAOFactory();
//        NodeDAO nodeDAO = daoFactory.getNodeDAO();

        NodeDAO nodeDAO = new NodeDAO(this);
        try {
            nodeDAO.addNode(node);
        } catch (Exception e) {
            throw new NodeStorageException("SQL storage failed to store node", e);
        }
    }
}
