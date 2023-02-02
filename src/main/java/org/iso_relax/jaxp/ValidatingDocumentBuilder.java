package org.iso_relax.jaxp;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import org.iso_relax.verifier.Verifier;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.*;

class ValidatingDocumentBuilder extends DocumentBuilder {

    protected ValidatingDocumentBuilder(DocumentBuilder documentbuilder, Verifier verifier) {
        _WrappedBuilder = documentbuilder;
        _Verifier = verifier;
    }

    public Document parse(InputSource inputsource)
        throws SAXException, IOException {
        return verify(_WrappedBuilder.parse(inputsource));
    }

    public Document parse(File file)
        throws SAXException, IOException {
        return verify(_WrappedBuilder.parse(file));
    }

    public Document parse(InputStream inputstream)
        throws SAXException, IOException {
        return verify(_WrappedBuilder.parse(inputstream));
    }

    public Document parse(InputStream inputstream, String s)
        throws SAXException, IOException {
        return verify(_WrappedBuilder.parse(inputstream, s));
    }

    public Document parse(String s)
        throws SAXException, IOException {
        return verify(_WrappedBuilder.parse(s));
    }

    public boolean isNamespaceAware() {
        return _WrappedBuilder.isNamespaceAware();
    }

    public boolean isValidating() {
        return true;
    }

    public void setEntityResolver(EntityResolver entityresolver) {
        _WrappedBuilder.setEntityResolver(entityresolver);
        _Verifier.setEntityResolver(entityresolver);
    }

    public void setErrorHandler(ErrorHandler errorhandler) {
        _WrappedBuilder.setErrorHandler(errorhandler);
        _Verifier.setErrorHandler(errorhandler);
    }

    public Document newDocument() {
        return _WrappedBuilder.newDocument();
    }

    public DOMImplementation getDOMImplementation() {
        return _WrappedBuilder.getDOMImplementation();
    }

    private Document verify(Document document)
        throws SAXException, IOException {
        if(_Verifier.verify(document))
            return document;
        else
            throw new SAXException("the document is invalid, but the ErrorHandler does not throw any Exception.");
    }

    protected DocumentBuilder _WrappedBuilder;
    protected Verifier _Verifier;
}
