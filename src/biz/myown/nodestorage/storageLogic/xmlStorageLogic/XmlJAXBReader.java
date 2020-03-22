package biz.myown.nodestorage.storagelogic.xmlstoragelogic;

import biz.myown.nodestorage.node.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlJAXBReader {

    private String filename;

    public XmlJAXBReader(String filename) {
        this.filename = filename;
    }

    public Node process() throws JAXBException {
        Node node = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        node = (Node) jaxbUnmarshaller.unmarshal(new File(filename));
        return node;
    }
}
