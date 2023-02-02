package org.iso_relax.verifier;

import java.io.PrintStream;
import java.io.PrintWriter;

public class VerifierConfigurationException extends Exception {

    public VerifierConfigurationException(String s) {
        super(s);
        cause_ = null;
    }

    public VerifierConfigurationException(Exception exception) {
        super(exception.getMessage());
        cause_ = null;
        cause_ = exception;
    }

    public VerifierConfigurationException(String s, Exception exception) {
        super(s);
        cause_ = null;
        cause_ = exception;
    }

    public Exception getException() {
        if(cause_ != null)
            return cause_;
        else
            return this;
    }

    public Exception getCauseException() {
        return cause_;
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
        if(cause_ != null) {
            printwriter.println();
            printwriter.println("StackTrace of Original Exception:");
            cause_.printStackTrace(printwriter);
        }
    }

    private Exception cause_;
}
