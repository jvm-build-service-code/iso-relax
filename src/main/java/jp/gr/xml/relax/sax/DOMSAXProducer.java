package jp.gr.xml.relax.sax;

import jp.gr.xml.relax.dom.DOMVisitorException;
import jp.gr.xml.relax.dom.UDOMVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.*;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

// Referenced classes of package jp.gr.xml.relax.sax:
//            DOMSAXProducerVisitor

public class DOMSAXProducer {

    public DOMSAXProducer(Node node) {
        needDocumentEmulation_ = true;
        root_ = node;
    }

    public void setDocumentEmulation(boolean flag) {
        needDocumentEmulation_ = flag;
    }

    public void setDTDHandler(DTDHandler dtdhandler) {
        dtd_ = dtdhandler;
    }

    public void setContentHandler(ContentHandler contenthandler) {
        content_ = contenthandler;
    }

    public void setLexicalHandler(LexicalHandler lexicalhandler) {
        lexical_ = lexicalhandler;
    }

    public void setDeclHandler(DeclHandler declhandler) {
        decl_ = declhandler;
    }

    public void setErrorHandler(ErrorHandler errorhandler) {
        error_ = errorhandler;
    }

    public void makeEvent()
        throws SAXException {
        try {
            DOMSAXProducerVisitor domsaxproducervisitor = new DOMSAXProducerVisitor();
            domsaxproducervisitor.setSystemID(systemID_);
            domsaxproducervisitor.setPublicID(publicID_);
            domsaxproducervisitor.setDTDHandler(dtd_);
            domsaxproducervisitor.setContentHandler(content_);
            domsaxproducervisitor.setLexicalHandler(lexical_);
            domsaxproducervisitor.setDeclHandler(decl_);
            domsaxproducervisitor.setErrorHandler(error_);
            if(!(root_ instanceof Document) && needDocumentEmulation_) {
                domsaxproducervisitor.emulateStartDocument();
                UDOMVisitor.traverse(root_, domsaxproducervisitor);
                domsaxproducervisitor.emulateEndDocument();
            } else {
                UDOMVisitor.traverse(root_, domsaxproducervisitor);
            }
        }
        catch(DOMVisitorException domvisitorexception) {
            Exception exception = domvisitorexception.getCauseException();
            if(exception == null)
                throw new SAXException(domvisitorexception.getMessage());
            if(exception instanceof SAXException)
                throw (SAXException)exception;
            else
                throw new SAXException(domvisitorexception.getMessage());
        }
    }

    public void makeEvent(ContentHandler contenthandler)
        throws SAXException {
        setContentHandler(contenthandler);
        makeEvent();
    }

    private boolean needDocumentEmulation_;
    private Node root_;
    private String systemID_;
    private String publicID_;
    private DTDHandler dtd_;
    private ContentHandler content_;
    private DeclHandler decl_;
    private LexicalHandler lexical_;
    private ErrorHandler error_;
}
