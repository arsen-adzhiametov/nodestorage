package biz.myown.nodestorage.impl;

import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlDOMReader;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlDOMWriter;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlJAXBReader;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlJAXBWriter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlNodeStorageTest {

    private String filename;
    private Node node;

    @Before
    public void setUp() throws Exception {
        filename = "xmlout/file.xml";
        node= NodesStructure.getNode();
    }

    @Test
        public void testReadJAXB() throws Exception {
        XmlJAXBReader xmlJAXBReader = new XmlJAXBReader(filename);
        assertEquals(xmlJAXBReader.process(), NodesStructure.getNode());
    }

    @Test
    public void testStoreJAXB() throws Exception {
        new XmlJAXBWriter(node, filename);
    }

    @Test
    public void testReadDOM() throws Exception {
        XmlDOMReader xmlDOMReader = new XmlDOMReader(filename);
        assertEquals(xmlDOMReader.process(), NodesStructure.getNode());
    }

    @Test
    public void testStoreDOM() throws Exception {
        new XmlDOMWriter(node, filename);
    }
}
