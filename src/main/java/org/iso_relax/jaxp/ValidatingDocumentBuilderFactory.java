package org.iso_relax.jaxp;

import javax.xml.parsers.*;
import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.VerifierConfigurationException;

// Referenced classes of package org.iso_relax.jaxp:
//            ValidatingDocumentBuilder

public class ValidatingDocumentBuilderFactory extends DocumentBuilderFactory {

    protected ValidatingDocumentBuilderFactory(DocumentBuilderFactory documentbuilderfactory, Schema schema) {
        validation = true;
        _WrappedFactory = documentbuilderfactory;
        _Schema = schema;
    }

    public DocumentBuilder newDocumentBuilder()
        throws ParserConfigurationException {
        if(isValidating())
            try {
                return new ValidatingDocumentBuilder(_WrappedFactory.newDocumentBuilder(), _Schema.newVerifier());
            }
            catch(VerifierConfigurationException verifierconfigurationexception) {
                throw new ParserConfigurationException(verifierconfigurationexception.getMessage());
            }
        else
            return _WrappedFactory.newDocumentBuilder();
    }

    public void setAttribute(String s, Object obj) {
        _WrappedFactory.setAttribute(s, obj);
    }

    public Object getAttribute(String s) {
        return _WrappedFactory.getAttribute(s);
    }

    public boolean isValidating() {
        return validation;
    }

    public void setValidating(boolean flag) {
        validation = flag;
    }

    public boolean isCoalescing() {
        return _WrappedFactory.isCoalescing();
    }

    public boolean isExpandEntityReference() {
        return _WrappedFactory.isExpandEntityReferences();
    }

    public boolean isIgnoringComments() {
        return _WrappedFactory.isIgnoringComments();
    }

    public boolean isIgnoringElementContentWhitespace() {
        return _WrappedFactory.isIgnoringElementContentWhitespace();
    }

    public boolean isNamespaceAware() {
        return _WrappedFactory.isNamespaceAware();
    }

    public void setCoalescing(boolean flag) {
        _WrappedFactory.setCoalescing(flag);
    }

    public void setExpandEntityReference(boolean flag) {
        _WrappedFactory.setExpandEntityReferences(flag);
    }

    public void setIgnoringComments(boolean flag) {
        _WrappedFactory.setIgnoringComments(flag);
    }

    public void setIgnoringElementContentWhitespace(boolean flag) {
        _WrappedFactory.setIgnoringElementContentWhitespace(flag);
    }

    public void setNamespaceAware(boolean flag) {
        _WrappedFactory.setNamespaceAware(flag);
    }

    public static ValidatingDocumentBuilderFactory newInstance(Schema schema) {
        return new ValidatingDocumentBuilderFactory(DocumentBuilderFactory.newInstance(), schema);
    }

    protected Schema _Schema;
    protected DocumentBuilderFactory _WrappedFactory;
    private boolean validation;

    @Override
    public void setFeature(String name, boolean value) throws ParserConfigurationException {
        // ignore
    }

    @Override
    public boolean getFeature(String name) throws ParserConfigurationException {
        // ignore
        return false;
    }
}
