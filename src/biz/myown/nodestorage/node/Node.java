package biz.myown.nodestorage.node;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Node implements Serializable {

    public transient byte version = 100;
    public transient byte count = 0;

    private String data;
    private List<Node> childNodes = new ArrayList<>();

    public String getData() {
        return data;
    }

    @XmlAttribute
    public void setData(String data) {
        this.data = data;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    @XmlElement(name= "node")
    public void setChildNodes(List<Node> childNodes) {
        this.childNodes = childNodes;
    }

    @Override
    public String toString() {
        return getData() + getChildNodes();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) return false;
        Node node = (Node)obj;
        if (this.data.equals(node.data) && (this.childNodes.equals(node.childNodes))) return true;
        return false;
    }
}
