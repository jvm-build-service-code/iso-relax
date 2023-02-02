package org.iso_relax.dispatcher.impl;

import org.iso_relax.dispatcher.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class IgnoreVerifier extends DefaultHandler
    implements IslandVerifier {

    public IgnoreVerifier(String s, ElementDecl aelementdecl[]) {
        namespaceToIgnore = s;
        rules = aelementdecl;
    }

    public ElementDecl[] endIsland() {
        return rules;
    }

    public void endChildIsland(String s, ElementDecl aelementdecl[]) {
    }

    public void setDispatcher(Dispatcher dispatcher1) {
        dispatcher = dispatcher1;
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
        throws SAXException {
        if(namespaceToIgnore.equals(s))
            return;
        IslandSchema islandschema = dispatcher.getSchemaProvider().getSchemaByNamespace(s);
        if(islandschema == null) {
            return;
        } else {
            IslandVerifier islandverifier = islandschema.createNewVerifier(s, islandschema.getElementDecls());
            dispatcher.switchVerifier(islandverifier);
            islandverifier.startElement(s, s1, s2, attributes);
            return;
        }
    }

    private final ElementDecl rules[];
    private final String namespaceToIgnore;
    private Dispatcher dispatcher;
}
