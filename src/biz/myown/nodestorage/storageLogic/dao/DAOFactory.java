package biz.myown.nodestorage.storagelogic.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

    private static final Properties prop = new Properties();

    static{
        try {
            prop.load(new FileReader("dao.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String driver = prop.getProperty("javabase.jdbc.driver");

        Class.forName(driver);

        String url = prop.getProperty("javabase.jdbc.url");
        String user = prop.getProperty("javabase.jdbc.username");
        String pass = prop.getProperty("javabase.jdbc.password");
        return DriverManager.getConnection(url, user, pass);
    }

    public NodeDAO getNodeDAO() {
        return new NodeDAO(this);
    }

}
