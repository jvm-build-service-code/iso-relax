package jp.gr.xml.relax.dom;

import jp.gr.xml.relax.xml.UXML;
import org.w3c.dom.*;

// Referenced classes of package jp.gr.xml.relax.dom:
//            IDOMVisitor, UDOMVisitor

public class XMLMaker
    implements IDOMVisitor {

    public XMLMaker() {
        encoding_ = "UTF-8";
        dom2_ = false;
        expandEntityReference_ = false;
        emptyElementTag_ = false;
        buffer_ = new StringBuffer();
    }

    public void setEncoding(String s) {
        encoding_ = s;
    }

    public void setDOM2(boolean flag) {
        dom2_ = flag;
    }

    public void setExpandEntityReference(boolean flag) {
        expandEntityReference_ = flag;
    }

    public void setEmptyElementTag(boolean flag) {
        emptyElementTag_ = flag;
    }

    public String getText() {
        return new String(buffer_);
    }

    public boolean enter(Element element) {
        String s = element.getTagName();
        buffer_.append("<");
        buffer_.append(s);
        NamedNodeMap namednodemap = element.getAttributes();
        int i = namednodemap.getLength();
        for(int j = 0; j < i; j++) {
            Attr attr = (Attr)namednodemap.item(j);
            if(attr.getSpecified()) {
                buffer_.append(' ');
                enter(attr);
                leave(attr);
            }
        }

        buffer_.append(">");
        return true;
    }

    public void leave(Element element) {
        String s = element.getTagName();
        buffer_.append("</" + s + ">");
    }

    public boolean enter(Attr attr) {
        buffer_.append(attr.getName());
        buffer_.append("=\"");
        buffer_.append(UXML.escapeAttrQuot(attr.getValue()));
        buffer_.append('"');
        return true;
    }

    public void leave(Attr attr) {
    }

    public boolean enter(Text text) {
        buffer_.append(UXML.escapeCharData(text.getData()));
        return true;
    }

    public void leave(Text text) {
    }

    public boolean enter(CDATASection cdatasection) {
        buffer_.append("<![CDATA[");
        buffer_.append(cdatasection.getData());
        buffer_.append("]]>");
        return true;
    }

    public void leave(CDATASection cdatasection) {
    }

    public boolean enter(EntityReference entityreference) {
        buffer_.append("&");
        buffer_.append(entityreference.getNodeName());
        buffer_.append(";");
        return false;
    }

    public void leave(EntityReference entityreference) {
    }

    public boolean enter(Entity entity) {
        String s = entity.getNodeName();
        String s1 = entity.getPublicId();
        String s2 = entity.getSystemId();
        String s3 = entity.getNotationName();
        buffer_.append("<!ENTITY ");
        buffer_.append(s);
        if(s2 != null) {
            if(s1 != null) {
                buffer_.append(" PUBLIC \"");
                buffer_.append(s1);
                buffer_.append("\" \"");
                buffer_.append(UXML.escapeSystemQuot(s2));
                buffer_.append("\">");
            } else {
                buffer_.append(" SYSTEM \"");
                buffer_.append(UXML.escapeSystemQuot(s2));
                buffer_.append("\">");
            }
            if(s3 != null) {
                buffer_.append(" NDATA ");
                buffer_.append(s3);
                buffer_.append(">");
            }
        } else {
            buffer_.append(" \"");
            XMLMaker xmlmaker = new XMLMaker();
            UDOMVisitor.traverseChildren(entity, xmlmaker);
            buffer_.append(UXML.escapeEntityQuot(xmlmaker.getText()));
            buffer_.append("\"");
            buffer_.append(">");
        }
        return false;
    }

    public void leave(Entity entity) {
    }

    public boolean enter(ProcessingInstruction processinginstruction) {
        buffer_.append("<?");
        buffer_.append(processinginstruction.getTarget());
        buffer_.append(" ");
        buffer_.append(processinginstruction.getData());
        buffer_.append("?>");
        return true;
    }

    public void leave(ProcessingInstruction processinginstruction) {
    }

    public boolean enter(Comment comment) {
        buffer_.append("<!--");
        buffer_.append(comment.getData());
        buffer_.append("-->");
        return true;
    }

    public void leave(Comment comment) {
    }

    public boolean enter(Document document) {
        buffer_.append("<?xml version=\"1.0\" encoding=\"");
        buffer_.append(encoding_);
        buffer_.append("\" ?>\n");
        return true;
    }

    public void leave(Document document) {
    }

    public boolean enter(DocumentType documenttype) {
        if(dom2_) {
            String s = documenttype.getName();
            String s2 = documenttype.getPublicId();
            String s3 = documenttype.getSystemId();
            String s4 = documenttype.getInternalSubset();
            buffer_.append("<!DOCTYPE ");
            buffer_.append(s);
            if(s2 != null) {
                buffer_.append(" PUBLIC \"");
                buffer_.append(s2);
                buffer_.append("\"");
            }
            if(s3 != null) {
                buffer_.append(" SYSTEM \"");
                buffer_.append(s3);
                buffer_.append("\"");
            }
            if(s4 != null) {
                buffer_.append(" [");
                buffer_.append(s4);
                buffer_.append("]");
            }
            buffer_.append(">\n");
            return true;
        }
        String s1 = documenttype.getName();
        NamedNodeMap namednodemap = documenttype.getEntities();
        NamedNodeMap namednodemap1 = documenttype.getNotations();
        buffer_.append("<!DOCTYPE ");
        buffer_.append(s1);
        if(namednodemap != null && namednodemap.getLength() > 0 || namednodemap1 != null && namednodemap1.getLength() > 0) {
            buffer_.append(" [");
            int i = namednodemap.getLength();
            for(int j = 0; j < i; j++) {
                XMLMaker xmlmaker = new XMLMaker();
                UDOMVisitor.traverse(namednodemap.item(j), xmlmaker);
                buffer_.append(xmlmaker.getText());
            }

            int k = namednodemap1.getLength();
            for(int l = 0; l < k; l++) {
                enter((Notation)namednodemap1.item(l));
                leave((Notation)namednodemap1.item(l));
            }

            buffer_.append("]");
        }
        buffer_.append(">\n");
        return true;
    }

    public void leave(DocumentType documenttype) {
    }

    public boolean enter(DocumentFragment documentfragment) {
        return true;
    }

    public void leave(DocumentFragment documentfragment) {
    }

    public boolean enter(Notation notation) {
        String s = notation.getNodeName();
        String s1 = notation.getPublicId();
        String s2 = notation.getSystemId();
        buffer_.append("<!NOTATION ");
        buffer_.append(s);
        if(s1 != null) {
            buffer_.append(" PUBLIC \"");
            buffer_.append(s1);
            buffer_.append("\"");
            if(s2 != null) {
                buffer_.append(" \"");
                buffer_.append(UXML.escapeSystemQuot(s2));
                buffer_.append("\"");
            }
        } else
        if(s2 != null) {
            buffer_.append(" SYSTEM \"");
            buffer_.append(UXML.escapeSystemQuot(s2));
            buffer_.append("\"");
        }
        buffer_.append(">");
        return true;
    }

    public void leave(Notation notation) {
    }

    public boolean enter(Node node) {
        throw new InternalError(node.toString());
    }

    public void leave(Node node) {
        throw new InternalError(node.toString());
    }

    public boolean isParsedEntity(EntityReference entityreference) {
        String s = entityreference.getNodeName();
        Document document = entityreference.getOwnerDocument();
        DocumentType documenttype = document.getDoctype();
        if(documenttype == null)
            return false;
        NamedNodeMap namednodemap = documenttype.getEntities();
        Entity entity = (Entity)namednodemap.getNamedItem(s);
        if(entity == null)
            return false;
        else
            return entity.getNotationName() == null;
    }

    protected StringBuffer buffer_;
    protected String encoding_;
    protected boolean dom2_;
    protected boolean expandEntityReference_;
    protected boolean emptyElementTag_;
}
