package org.iso_relax.verifier;

import java.io.File;
import java.io.IOException;
import org.w3c.dom.Node;
import org.xml.sax.*;

// Referenced classes of package org.iso_relax.verifier:
//            VerifierHandler, VerifierFilter

public interface Verifier {

    public abstract boolean isFeature(String s)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    public abstract void setFeature(String s, boolean flag)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    public abstract Object getProperty(String s)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    public abstract void setProperty(String s, Object obj)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    public abstract void setErrorHandler(ErrorHandler errorhandler);

    public abstract void setEntityResolver(EntityResolver entityresolver);

    public abstract boolean verify(String s)
        throws SAXException, IOException;

    public abstract boolean verify(InputSource inputsource)
        throws SAXException, IOException;

    public abstract boolean verify(File file)
        throws SAXException, IOException;

    public abstract boolean verify(Node node)
        throws SAXException;

    public abstract VerifierHandler getVerifierHandler()
        throws SAXException;

    public abstract VerifierFilter getVerifierFilter()
        throws SAXException;

    /**
     * @deprecated Field FEATURE_HANDLER is deprecated
     */
    public static final String FEATURE_HANDLER = "http://www.iso-relax.org/verifier/handler";
    /**
     * @deprecated Field FEATURE_FILTER is deprecated
     */
    public static final String FEATURE_FILTER = "http://www.iso-relax.org/verifier/filter";
}
