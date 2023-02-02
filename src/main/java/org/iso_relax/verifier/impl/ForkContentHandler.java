package org.iso_relax.verifier.impl;

import org.xml.sax.*;

public class ForkContentHandler
    implements ContentHandler {

    public ForkContentHandler(ContentHandler contenthandler, ContentHandler contenthandler1) {
        lhs = contenthandler;
        rhs = contenthandler1;
    }

    public static ContentHandler create(ContentHandler acontenthandler[]) {
        if(acontenthandler.length == 0)
            throw new IllegalArgumentException();
        Object obj = acontenthandler[0];
        for(int i = 1; i < acontenthandler.length; i++)
            obj = new ForkContentHandler(((ContentHandler) (obj)), acontenthandler[i]);

        return ((ContentHandler) (obj));
    }

    public void setDocumentLocator(Locator locator) {
        lhs.setDocumentLocator(locator);
        rhs.setDocumentLocator(locator);
    }

    public void startDocument()
        throws SAXException {
        lhs.startDocument();
        rhs.startDocument();
    }

    public void endDocument()
        throws SAXException {
        lhs.endDocument();
        rhs.endDocument();
    }

    public void startPrefixMapping(String s, String s1)
        throws SAXException {
        lhs.startPrefixMapping(s, s1);
        rhs.startPrefixMapping(s, s1);
    }

    public void endPrefixMapping(String s)
        throws SAXException {
        lhs.endPrefixMapping(s);
        rhs.endPrefixMapping(s);
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
        throws SAXException {
        lhs.startElement(s, s1, s2, attributes);
        rhs.startElement(s, s1, s2, attributes);
    }

    public void endElement(String s, String s1, String s2)
        throws SAXException {
        lhs.endElement(s, s1, s2);
        rhs.endElement(s, s1, s2);
    }

    public void characters(char ac[], int i, int j)
        throws SAXException {
        lhs.characters(ac, i, j);
        rhs.characters(ac, i, j);
    }

    public void ignorableWhitespace(char ac[], int i, int j)
        throws SAXException {
        lhs.ignorableWhitespace(ac, i, j);
        rhs.ignorableWhitespace(ac, i, j);
    }

    public void processingInstruction(String s, String s1)
        throws SAXException {
        lhs.processingInstruction(s, s1);
        rhs.processingInstruction(s, s1);
    }

    public void skippedEntity(String s)
        throws SAXException {
        lhs.skippedEntity(s);
        rhs.skippedEntity(s);
    }

    private final ContentHandler lhs;
    private final ContentHandler rhs;
}
