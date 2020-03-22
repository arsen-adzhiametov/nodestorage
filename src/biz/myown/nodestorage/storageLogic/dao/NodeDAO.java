package biz.myown.nodestorage.storagelogic.dao;

import biz.myown.nodestorage.impl.SqlNodeStorage;
import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.nodevisitor.NodeCallBack;
import biz.myown.nodestorage.nodevisitor.NodeTraverser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static biz.myown.nodestorage.storagelogic.dao.DAOJDBCUtil.close;

public class NodeDAO {

    private static final String SQL_FIND_ROOT_NODE = "select * from node where parent_node_id is NULL";
    private static final String SQL_FIND_ALL_CHILDS = "select * from node where parent_node_id = (?)";
    private static final String SQL_FIND_NODE_ID_BY_DATA = "select id from node where data = (?)";
    private static final String SQL_FIND_NODE_BY_ID = "select * from node where id = (?)";
    private static final String SQL_FIND_NODE_BY_NULL = "select * from node where parent_node_id is NULL";
    private static final String SQL_INSERT_NODE = "INSERT INTO node (data, parent_node_id) VALUES (?, ?)";
    private static final String SQL_INSERT_ROOT_NODE = "INSERT INTO node (data) VALUES (?)";

    private DAOFactory daoFactory;
    private SqlNodeStorage nodeStorage;

    public NodeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public NodeDAO(SqlNodeStorage nodeStorage) {
        this.nodeStorage = nodeStorage;
    }
//    public void addNode(Node node, Integer parentId) {
//        Integer id = add(node, parentId);
//        for (int i = 0; i < node.getChildNodes().size(); i++) {
//            addNode(node.getChildNodes().get(i), id);
//        }
//    }

    public void addNode(Node node) throws Exception {

        NodeTraverser.traverseNode(node, new NodeCallBack() {
            @Override
            public Object before(Node node, Object parentNode) throws Exception {
                Integer id = null;
                if (parentNode != null) {
                    id = add(node, findNodeIdByData(((Node) parentNode).getData()));
                } else {
                    id = add(node, null);
                }
                return findNodeById(id);
            }

            @Override
            public void after(Node node) throws Exception {
            }
        }, null);
    }

    private Integer add(Node node, Integer parentId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer nodeId = null;
        try {
//            connection = daoFactory.getConnection();
            connection = nodeStorage.getConnection();
            if (parentId == null) {
                preparedStatement = connection.prepareStatement(SQL_INSERT_ROOT_NODE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, node.getData());
            } else {
                preparedStatement = connection.prepareStatement(SQL_INSERT_NODE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, node.getData());
                preparedStatement.setInt(2, parentId);
            }
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) nodeId = resultSet.getInt(1);
            return nodeId;
        } finally {
            close(preparedStatement);
            close(connection);
        }

    }

    public Integer findNodeIdByData(String data) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer nodeId = null;
        try {
//            connection = daoFactory.getConnection();
            connection = nodeStorage.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_NODE_ID_BY_DATA);
            preparedStatement.setString(1, data);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nodeId = resultSet.getInt(1);
            }

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return nodeId;
    }

    public Node findNodeById(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Node node = null;
        try {
//            connection = daoFactory.getConnection();
            connection = nodeStorage.getConnection();
            if (id != null) {
                preparedStatement = connection.prepareStatement(SQL_FIND_NODE_BY_ID);
                preparedStatement.setInt(1, id);
            } else preparedStatement = connection.prepareStatement(SQL_FIND_NODE_BY_NULL);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                node = map(resultSet);
            }

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return node;
    }

    public Node findNode() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Node node = null;
        try {
//            connection = daoFactory.getConnection();
            connection = nodeStorage.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ROOT_NODE);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                node = map(resultSet);
            }

        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return node;
    }

    public List<Node> findAll(Integer parentNodeId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Node> nodes = new ArrayList();
        try {
//            connection = daoFactory.getConnection();
            connection = nodeStorage.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_CHILDS);
            preparedStatement.setInt(1, parentNodeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nodes.add(map(resultSet));
            }
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return nodes;
    }

    private Node map(ResultSet resultSet) throws SQLException {
        Node node = new Node();
        node.setData(resultSet.getString(2));
        node.setChildNodes(findAll(resultSet.getInt(1)));
        return node;
    }
}
