package org.iso_relax.verifier;

import org.xml.sax.ContentHandler;

public interface VerifierHandler
    extends ContentHandler {

    public abstract boolean isValid()
        throws IllegalStateException;
}
