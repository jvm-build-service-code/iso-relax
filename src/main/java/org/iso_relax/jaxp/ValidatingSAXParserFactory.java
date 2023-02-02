package org.iso_relax.jaxp;

import javax.xml.parsers.*;
import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.VerifierConfigurationException;
import org.xml.sax.*;

// Referenced classes of package org.iso_relax.jaxp:
//            ValidatingSAXParser

public class ValidatingSAXParserFactory extends SAXParserFactory {

    protected ValidatingSAXParserFactory(SAXParserFactory saxparserfactory, Schema schema) {
        validation = true;
        _WrappedFactory = saxparserfactory;
        _Schema = schema;
    }

    public SAXParser newSAXParser()
        throws ParserConfigurationException, SAXException {
        if(_WrappedFactory.isValidating())
            try {
                return new ValidatingSAXParser(_WrappedFactory.newSAXParser(), _Schema.newVerifier());
            }
            catch(VerifierConfigurationException verifierconfigurationexception) {
                throw new ParserConfigurationException(verifierconfigurationexception.getMessage());
            }
        else
            return _WrappedFactory.newSAXParser();
    }

    public void setFeature(String s, boolean flag)
        throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        _WrappedFactory.setFeature(s, flag);
    }

    public boolean getFeature(String s)
        throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        return _WrappedFactory.getFeature(s);
    }

    public boolean isNamespaceAware() {
        return _WrappedFactory.isNamespaceAware();
    }

    public void setNamespaceAware(boolean flag) {
        _WrappedFactory.setNamespaceAware(flag);
    }

    public boolean isValidating() {
        return validation;
    }

    public void setValidating(boolean flag) {
        validation = flag;
    }

    public static ValidatingSAXParserFactory newInstance(Schema schema) {
        return new ValidatingSAXParserFactory(SAXParserFactory.newInstance(), schema);
    }

    protected SAXParserFactory _WrappedFactory;
    protected Schema _Schema;
    private boolean validation;
}
