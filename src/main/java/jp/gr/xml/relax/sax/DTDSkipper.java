package jp.gr.xml.relax.sax;

import java.io.StringReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class DTDSkipper
    implements EntityResolver {

    public DTDSkipper() {
    }

    public InputSource resolveEntity(String s, String s1) {
        if(!s1.endsWith(".dtd")) {
            return null;
        } else {
            StringReader stringreader = new StringReader("");
            InputSource inputsource = new InputSource(stringreader);
            return inputsource;
        }
    }
}
