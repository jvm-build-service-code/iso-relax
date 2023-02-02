package org.iso_relax.verifier;

import org.xml.sax.XMLFilter;

public interface VerifierFilter
    extends XMLFilter {

    public abstract boolean isValid()
        throws IllegalStateException;
}
