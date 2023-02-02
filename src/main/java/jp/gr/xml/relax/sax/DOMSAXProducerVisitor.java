package jp.gr.xml.relax.sax;

import jp.gr.xml.relax.dom.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.*;

// Referenced classes of package jp.gr.xml.relax.sax:
//            LexicalHandlerBase, DeclHandlerBase

public class DOMSAXProducerVisitor
    implements IDOMVisitor {

    public DOMSAXProducerVisitor() {
        DefaultHandler defaulthandler = new DefaultHandler();
        dtd_ = defaulthandler;
        content_ = defaulthandler;
        error_ = defaulthandler;
        lexical_ = new LexicalHandlerBase();
        decl_ = new DeclHandlerBase();
        namespace_ = new NamespaceSupport();
        throwException_ = false;
    }

    public void setSystemID(String s) {
        systemID_ = s;
    }

    public void setPublicID(String s) {
        publicID_ = s;
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

    public void emulateStartDocument() {
        try {
            _handleLocator();
            content_.startDocument();
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    public void emulateEndDocument() {
        try {
            content_.endDocument();
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    public void throwException(boolean flag) {
        throwException_ = flag;
    }

    public boolean enter(Element element) {
        try {
            namespace_.pushContext();
            String s = element.getNamespaceURI();
            if(s == null)
                s = "";
            String s1 = element.getLocalName();
            String s2 = element.getTagName();
            NamedNodeMap namednodemap = element.getAttributes();
            AttributesImpl attributesimpl = new AttributesImpl();
            int i = namednodemap.getLength();
            for(int j = 0; j < i; j++) {
                Attr attr = (Attr)namednodemap.item(j);
                String s3 = attr.getNamespaceURI();
                if(s3 == null)
                    s3 = "";
                String s4 = attr.getLocalName();
                String s5 = attr.getName();
                String s6 = attr.getValue();
                if(s5.startsWith("xmlns")) {
                    int k = s5.indexOf(':');
                    String s7;
                    if(k == -1)
                        s7 = "";
                    else
                        s7 = s5.substring(k + 1);
                    if(!namespace_.declarePrefix(s7, s6))
                        _errorReport("bad prefix = " + s7);
                    else
                        content_.startPrefixMapping(s7, s6);
                } else {
                    attributesimpl.addAttribute(s3, s4, s5, "CDATA", s6);
                }
            }

            content_.startElement(s, s1, s2, attributesimpl);
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
        return true;
    }

    public boolean enter(Attr attr) {
        return false;
    }

    public boolean enter(Text text) {
        try {
            String s = text.getData();
            content_.characters(s.toCharArray(), 0, s.length());
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
        return false;
    }

    public boolean enter(CDATASection cdatasection) {
        try {
            lexical_.startCDATA();
            String s = cdatasection.getData();
            content_.characters(s.toCharArray(), 0, s.length());
            lexical_.endCDATA();
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
        return false;
    }

    public boolean enter(EntityReference entityreference) {
        try {
            lexical_.startEntity(entityreference.getNodeName());
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
        return true;
    }

    public boolean enter(Entity entity) {
        return false;
    }

    public boolean enter(ProcessingInstruction processinginstruction) {
        try {
            content_.processingInstruction(processinginstruction.getTarget(), processinginstruction.getData());
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
        return false;
    }

    public boolean enter(Comment comment) {
        try {
            String s = comment.getData();
            lexical_.comment(s.toCharArray(), 0, s.length());
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
        return false;
    }

    public boolean enter(Document document) {
        try {
            _handleLocator();
            content_.startDocument();
            _handleDoctype(document.getDoctype());
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
        return true;
    }

    private void _handleLocator() {
        if(systemID_ == null && publicID_ == null) {
            return;
        } else {
            _locatorEvent(systemID_, publicID_);
            return;
        }
    }

    private void _locatorEvent(String s, String s1) {
        LocatorImpl locatorimpl = new LocatorImpl();
        locatorimpl.setSystemId(systemID_);
        locatorimpl.setPublicId(publicID_);
        locatorimpl.setLineNumber(-1);
        locatorimpl.setColumnNumber(-1);
        content_.setDocumentLocator(locatorimpl);
    }

    private void _handleDoctype(DocumentType documenttype) {
        try {
            if(documenttype == null)
                return;
            String s = documenttype.getSystemId();
            String s1 = documenttype.getPublicId();
            String s2 = documenttype.getInternalSubset();
            if(s != null) {
                lexical_.startDTD(documenttype.getName(), s1, s);
                if(s2 == null) {
                    lexical_.endDTD();
                    _handleEntities(documenttype);
                } else {
                    _handleEntities(documenttype);
                    lexical_.endDTD();
                }
            } else {
                _handleEntities(documenttype);
            }
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    private void _handleEntities(DocumentType documenttype) {
        try {
            NamedNodeMap namednodemap = documenttype.getEntities();
            int i = namednodemap.getLength();
            for(int j = 0; j < i; j++) {
                Entity entity = (Entity)namednodemap.item(j);
                String s = entity.getPublicId();
                String s1 = entity.getSystemId();
                String s2 = entity.getNotationName();
                if(s != null || s1 != null)
                    _handleExternalEntity(entity.getNodeName(), s, s1, s2);
                else
                    _handleInternalEntity(entity);
            }

            NamedNodeMap namednodemap1 = documenttype.getNotations();
            int k = namednodemap1.getLength();
            for(int l = 0; l < k; l++) {
                Notation notation = (Notation)namednodemap1.item(l);
                String s3 = notation.getPublicId();
                String s4 = notation.getSystemId();
                dtd_.notationDecl(notation.getNodeName(), s3, s4);
            }

        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    private void _handleExternalEntity(String s, String s1, String s2, String s3) {
        try {
            if(s3 == null)
                decl_.externalEntityDecl(s, s1, s2);
            else
                dtd_.unparsedEntityDecl(s, s1, s2, s3);
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    private void _handleInternalEntity(Entity entity) {
        try {
            decl_.internalEntityDecl(entity.getNodeName(), UDOM.getXMLText(entity));
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    public boolean enter(DocumentType documenttype) {
        return false;
    }

    public boolean enter(DocumentFragment documentfragment) {
        return true;
    }

    public boolean enter(Notation notation) {
        return false;
    }

    public boolean enter(Node node) {
        return false;
    }

    public void leave(Element element) {
        try {
            String s = element.getNamespaceURI();
            if(s == null)
                s = "";
            String s1 = element.getLocalName();
            String s2 = element.getTagName();
            content_.endElement(s, s1, s2);
            namespace_.popContext();
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    public void leave(Attr attr) {
    }

    public void leave(Text text) {
    }

    public void leave(CDATASection cdatasection) {
    }

    public void leave(EntityReference entityreference) {
        try {
            lexical_.endEntity(entityreference.getNodeName());
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    public void leave(Entity entity) {
    }

    public void leave(ProcessingInstruction processinginstruction) {
    }

    public void leave(Comment comment) {
    }

    public void leave(Document document) {
        try {
            content_.endDocument();
        }
        catch(SAXException saxexception) {
            _errorReport(saxexception);
        }
    }

    public void leave(DocumentType documenttype) {
    }

    public void leave(DocumentFragment documentfragment) {
    }

    public void leave(Notation notation) {
    }

    public void leave(Node node) {
    }

    private void _errorReport(String s)
        throws DOMVisitorException {
        _errorReport(((SAXException) (new SAXParseException(s, publicID_, systemID_, -1, -1))));
    }

    private void _errorReport(SAXException saxexception)
        throws DOMVisitorException {
        try {
            SAXParseException saxparseexception;
            if(saxexception instanceof SAXParseException)
                saxparseexception = (SAXParseException)saxexception;
            else
                saxparseexception = new SAXParseException(saxexception.getMessage(), publicID_, systemID_, -1, -1, saxexception);
            error_.fatalError(saxparseexception);
            if(throwException_)
                throw new DOMVisitorException(saxexception);
        }
        catch(SAXException saxexception1) { }
    }

    private String systemID_;
    private String publicID_;
    private DTDHandler dtd_;
    private ContentHandler content_;
    private DeclHandler decl_;
    private LexicalHandler lexical_;
    private ErrorHandler error_;
    private NamespaceSupport namespace_;
    private boolean throwException_;
}
