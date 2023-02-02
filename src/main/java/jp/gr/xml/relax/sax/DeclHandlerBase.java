package jp.gr.xml.relax.sax;

import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

public class DeclHandlerBase
    implements DeclHandler {

    public DeclHandlerBase() {
    }

    public void elementDecl(String s, String s1)
        throws SAXException {
    }

    public void attributeDecl(String s, String s1, String s2, String s3, String s4)
        throws SAXException {
    }

    public void internalEntityDecl(String s, String s1)
        throws SAXException {
    }

    public void externalEntityDecl(String s, String s1, String s2)
        throws SAXException {
    }
}
