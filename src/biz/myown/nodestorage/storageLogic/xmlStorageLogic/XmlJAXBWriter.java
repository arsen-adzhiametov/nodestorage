package biz.myown.nodestorage.storagelogic.xmlstoragelogic;

import biz.myown.nodestorage.node.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XmlJAXBWriter {

    private String filename;

    public XmlJAXBWriter(Node root, String filename) throws JAXBException {
        this.filename = filename;
        process(root, filename);
    }

    private void process(Node root, String filename) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(root, new File(filename));
    }
}



