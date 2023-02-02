package org.iso_relax.verifier.impl;

import org.iso_relax.verifier.*;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;

public class VerifierFilterImpl extends XMLFilterImpl
    implements VerifierFilter {

    public VerifierFilterImpl(Verifier verifier1)
        throws SAXException {
        verifier = verifier1;
        core = verifier.getVerifierHandler();
    }

    public boolean isValid() {
        return core.isValid();
    }

    public void setErrorHandler(ErrorHandler errorhandler) {
        super.setErrorHandler(errorhandler);
        verifier.setErrorHandler(errorhandler);
    }

    public void setEntityResolver(EntityResolver entityresolver) {
        super.setEntityResolver(entityresolver);
        verifier.setEntityResolver(entityresolver);
    }

    public void setDocumentLocator(Locator locator) {
        core.setDocumentLocator(locator);
        super.setDocumentLocator(locator);
    }

    public void startDocument()
        throws SAXException {
        core.startDocument();
        super.startDocument();
    }

    public void endDocument()
        throws SAXException {
        core.endDocument();
        super.endDocument();
    }

    public void startPrefixMapping(String s, String s1)
        throws SAXException {
        core.startPrefixMapping(s, s1);
        super.startPrefixMapping(s, s1);
    }

    public void endPrefixMapping(String s)
        throws SAXException {
        core.endPrefixMapping(s);
        super.endPrefixMapping(s);
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
        throws SAXException {
        core.startElement(s, s1, s2, attributes);
        super.startElement(s, s1, s2, attributes);
    }

    public void endElement(String s, String s1, String s2)
        throws SAXException {
        core.endElement(s, s1, s2);
        super.endElement(s, s1, s2);
    }

    public void characters(char ac[], int i, int j)
        throws SAXException {
        core.characters(ac, i, j);
        super.characters(ac, i, j);
    }

    public void ignorableWhitespace(char ac[], int i, int j)
        throws SAXException {
        core.ignorableWhitespace(ac, i, j);
        super.ignorableWhitespace(ac, i, j);
    }

    public void processingInstruction(String s, String s1)
        throws SAXException {
        core.processingInstruction(s, s1);
        super.processingInstruction(s, s1);
    }

    public void skippedEntity(String s)
        throws SAXException {
        core.skippedEntity(s);
        super.skippedEntity(s);
    }

    private final Verifier verifier;
    private final VerifierHandler core;
}
