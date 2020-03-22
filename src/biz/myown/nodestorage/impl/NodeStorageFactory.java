package biz.myown.nodestorage.impl;

import biz.myown.nodestorage.storageinterface.NodeStorage;

public class NodeStorageFactory {

    public static enum StorageType {XML_DOM, XML_JAXB, SQL_JDBC}

    private static String filename = "xmlout/file.xml";

    public static NodeStorage getNodeStorage(StorageType storageType) {
        switch (storageType) {
            case XML_DOM:
                return new XmlNodeStorage(XmlNodeStorage.Parser.DOM, filename);

            case XML_JAXB:
                return new XmlNodeStorage(XmlNodeStorage.Parser.JAXB, filename);

            case SQL_JDBC:
                return new SqlNodeStorage(
                        "localhost",
                        3306,
                        "nodestorage",
                        "root",
                        "root");
        }
        return null;
    }
}
