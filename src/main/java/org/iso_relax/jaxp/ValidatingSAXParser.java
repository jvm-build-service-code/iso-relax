package org.iso_relax.jaxp;

import java.io.*;
import javax.xml.parsers.SAXParser;
import org.iso_relax.verifier.Verifier;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

class ValidatingSAXParser extends SAXParser {

    protected ValidatingSAXParser(SAXParser saxparser, Verifier verifier) {
        _WrappedParser = saxparser;
        _Verifier = verifier;
    }

    public Parser getParser() {
        throw new UnsupportedOperationException("getParser() method is not supported. Use getXMLReader().");
    }

    public XMLReader getXMLReader()
        throws SAXException {
        org.iso_relax.verifier.VerifierFilter verifierfilter = _Verifier.getVerifierFilter();
        verifierfilter.setParent(_WrappedParser.getXMLReader());
        return verifierfilter;
    }

    public boolean isNamespaceAware() {
        return _WrappedParser.isNamespaceAware();
    }

    public boolean isValidating() {
        return true;
    }

    public void setProperty(String s, Object obj)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        _WrappedParser.setProperty(s, obj);
    }

    public Object getProperty(String s)
        throws SAXNotRecognizedException, SAXNotSupportedException {
        return _WrappedParser.getProperty(s);
    }

    public void parse(File file, HandlerBase handlerbase) {
        throw new UnsupportedOperationException("SAX1 features are not supported");
    }

    public void parse(InputSource inputsource, HandlerBase handlerbase) {
        throw new UnsupportedOperationException("SAX1 features are not supported");
    }

    public void parse(InputStream inputstream, HandlerBase handlerbase) {
        throw new UnsupportedOperationException("SAX1 features are not supported");
    }

    public void parse(InputStream inputstream, HandlerBase handlerbase, String s) {
        throw new UnsupportedOperationException("SAX1 features are not supported");
    }

    public void parse(String s, HandlerBase handlerbase) {
        throw new UnsupportedOperationException("SAX1 features are not supported");
    }

    public void parse(File file, DefaultHandler defaulthandler)
        throws SAXException, IOException {
        XMLReader xmlreader = getXMLReader();
        InputSource inputsource = new InputSource(new FileInputStream(file));
        xmlreader.setContentHandler(defaulthandler);
        xmlreader.parse(inputsource);
    }

    public void parse(InputSource inputsource, DefaultHandler defaulthandler)
        throws SAXException, IOException {
        XMLReader xmlreader = getXMLReader();
        xmlreader.setContentHandler(defaulthandler);
        xmlreader.parse(inputsource);
    }

    public void parse(InputStream inputstream, DefaultHandler defaulthandler)
        throws SAXException, IOException {
        XMLReader xmlreader = getXMLReader();
        InputSource inputsource = new InputSource(inputstream);
        xmlreader.setContentHandler(defaulthandler);
        xmlreader.parse(inputsource);
    }

    public void parse(InputStream inputstream, DefaultHandler defaulthandler, String s)
        throws SAXException, IOException {
        XMLReader xmlreader = getXMLReader();
        InputSource inputsource = new InputSource(inputstream);
        inputsource.setSystemId(s);
        xmlreader.setContentHandler(defaulthandler);
        xmlreader.parse(inputsource);
    }

    public void parse(String s, DefaultHandler defaulthandler)
        throws SAXException, IOException {
        XMLReader xmlreader = getXMLReader();
        InputSource inputsource = new InputSource(s);
        xmlreader.setContentHandler(defaulthandler);
        xmlreader.parse(inputsource);
    }

    protected SAXParser _WrappedParser;
    protected Verifier _Verifier;
}
