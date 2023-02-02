package org.iso_relax.verifier;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.xml.sax.SAXException;

public class VerifierException extends SAXException {

    public VerifierException(String s) {
        super(s);
    }

    public VerifierException(Exception exception) {
        super(exception);
    }

    public VerifierException(String s, Exception exception) {
        super(s, exception);
    }

    public void printStackTrace() {
        printStackTrace(new PrintWriter(System.err, true));
    }

    public void printStackTrace(PrintStream printstream) {
        printStackTrace(new PrintWriter(printstream));
    }

    public void printStackTrace(PrintWriter printwriter) {
        if(printwriter == null)
            printwriter = new PrintWriter(System.err, true);
        super.printStackTrace(printwriter);
        Exception exception = super.getException();
        if(exception != null) {
            printwriter.println();
            printwriter.println("StackTrace of Original Exception:");
            exception.printStackTrace(printwriter);
        }
    }
}
