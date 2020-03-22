package biz.myown.nodestorage.storagelogic.xmlstoragelogic;

import biz.myown.nodestorage.node.Node;
import biz.myown.nodestorage.nodevisitor.NodeCallBack;
import biz.myown.nodestorage.nodevisitor.NodeTraverser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlDOMWriter {

    private String fileName;
    private Document document;

    public XmlDOMWriter(Node root, String fileName) throws Exception {
        this.fileName = fileName;
        writeXml(create(root));
    }

    private Document create(Node root) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
        buildDocument(root);
        return document;
    }

    private void buildDocument(Node node) throws Exception {

        NodeTraverser.traverseNode(node, new NodeCallBack() {
            @Override
            public Object before(Node node, Object parentNode) throws Exception {
                Element element = document.createElement("node");
                element.setAttribute("data", node.getData());
                if (parentNode != null) {
                    ((Element) parentNode).appendChild(element);
                } else {
                    document.appendChild(element);
                }
                parentNode = element;
                return parentNode;
            }

            @Override
            public void after(Node node) throws Exception {
            }
        }, null);
    }

//    private void buildDocument(Node child, Element parent) {
//        Element element = document.createElement("node");
//        element.setAttribute("data", child.getData());
//        if (parent != null) {
//            parent.appendChild(element);
//        } else {
//            document.appendChild(element);
//        }
//        parent = element;
//        for (int i = 0; i < child.getChildNodes().size(); i++) {
//            buildDocument(child.getChildNodes().get(i), parent);
//        }
//    }

    private void writeXml(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }
}
