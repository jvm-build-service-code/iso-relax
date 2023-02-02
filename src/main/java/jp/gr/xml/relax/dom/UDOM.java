package jp.gr.xml.relax.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

// Referenced classes of package jp.gr.xml.relax.dom:
//            XMLMaker, UDOMVisitor

public final class UDOM {

    public UDOM() {
    }

    public static String getXMLText(Document document) {
        return getXMLText(document, "UTF-8");
    }

    public static String getXMLText(Document document, String s) {
        XMLMaker xmlmaker = new XMLMaker();
        UDOMVisitor.traverse(document, xmlmaker);
        return xmlmaker.getText();
    }

    public static String getXMLText(Node node) {
        XMLMaker xmlmaker = new XMLMaker();
        UDOMVisitor.traverse(node, xmlmaker);
        return xmlmaker.getText();
    }
}
