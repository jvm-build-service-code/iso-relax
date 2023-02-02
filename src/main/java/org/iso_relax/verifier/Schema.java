package org.iso_relax.verifier;


// Referenced classes of package org.iso_relax.verifier:
//            VerifierConfigurationException, Verifier

public interface Schema {

    public abstract Verifier newVerifier()
        throws VerifierConfigurationException;
}
