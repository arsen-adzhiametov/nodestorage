package biz.myown.nodestorage.storagelogic.xmlstoragelogic;

import biz.myown.nodestorage.node.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlDOMReader {

    private String filename;
    private Node result;

    public XmlDOMReader(String filename) {
        this.filename = filename;
    }

    private Document parserXML(File file) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
    }

    public Node process() throws ParserConfigurationException, SAXException, IOException {
        Document doc = parserXML(new File(filename));
        visit(doc, null);
        return result;
    }

    private void visit(org.w3c.dom.Node element, Node parent) {

        NodeList nl = element.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {

            if (nl.item(i).getNodeName().equals("node")) {

                Node node = getNode((Element) nl.item(i));

                if (result == null) result = node;

                if (parent != null)
                    parent.getChildNodes().add(node);

                visit(nl.item(i), node);
            }
        }
    }

    private Node getNode(Element element) {
        Node node = new Node();
        NamedNodeMap map = element.getAttributes();
        for (int j = 0; j < map.getLength(); j++) {
            if (map.item(j).getNodeName() == "data") {
                node.setData(map.item(j).getNodeValue());
            }
        }
        return node;
    }
}
