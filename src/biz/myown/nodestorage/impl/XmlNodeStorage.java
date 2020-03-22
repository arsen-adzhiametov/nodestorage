package biz.myown.nodestorage.impl;

import biz.myown.nodestorage.exception.NodeStorageException;
import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.storageinterface.NodeStorage;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlDOMReader;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlDOMWriter;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlJAXBReader;
import biz.myown.nodestorage.storagelogic.xmlstoragelogic.XmlJAXBWriter;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlNodeStorage implements NodeStorage {

    public static enum Parser {DOM, JAXB}

    private final String xsdFilename = "src\\biz\\myown\\nodestorage\\xsdshema\\file.xsd";

    private String filename = null;
    private Parser parser;

    public XmlNodeStorage(Parser parser, String filename) {
        this.filename = filename;
        this.parser = parser;
    }

    @Override
    public Node read() throws NodeStorageException {

        try {
            validate(filename, xsdFilename);
        } catch (SAXException e) {
            throw new NodeStorageException("Failed to validate file", e);
        } catch (IOException e) {
            throw new NodeStorageException("File not found", e);
        }

        switch (parser) {

            case DOM:
                try {
                    return new XmlDOMReader(filename).process();
                } catch (Exception e) {
                    throw new NodeStorageException("Failed to parse document", e);
                }

            case JAXB:
                try {
                    return new XmlJAXBReader(filename).process();
                } catch (JAXBException e) {
                    throw new NodeStorageException("Failed to parse document", e);
                }

            default:
                return null;
        }
    }

    @Override
    public void store(Node node) throws NodeStorageException {
        switch (parser) {

            case DOM:
                try {
                    new XmlDOMWriter(node, filename);
                } catch (Exception e) {
                    throw new NodeStorageException("Failed to write document", e);
                }

            case JAXB:
                try {
                    new XmlJAXBWriter(node, filename);
                } catch (JAXBException e) {
                    throw new NodeStorageException("Failed to write document", e);
                }
        }
    }

    private void validate(String xmlFile, String xsdFilename) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = factory.newSchema(new File(xsdFilename));
        Validator validator = schema.newValidator();
        Source source = new StreamSource(xmlFile);
        try {
            validator.validate(source);
            return;
        } catch (SAXException ex) {
            throw new UnsupportedOperationException();
        }
    }
}
