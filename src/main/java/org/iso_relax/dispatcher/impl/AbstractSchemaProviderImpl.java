package org.iso_relax.dispatcher.impl;

import java.util.*;
import org.iso_relax.dispatcher.IslandSchema;
import org.iso_relax.dispatcher.SchemaProvider;

public abstract class AbstractSchemaProviderImpl
    implements SchemaProvider {

    public AbstractSchemaProviderImpl() {
    }

    public void addSchema(String s, IslandSchema islandschema) {
        if(schemata.containsKey(s)) {
            throw new IllegalArgumentException();
        } else {
            schemata.put(s, islandschema);
            return;
        }
    }

    public IslandSchema getSchemaByNamespace(String s) {
        return (IslandSchema)schemata.get(s);
    }

    public Iterator iterateNamespace() {
        return schemata.keySet().iterator();
    }

    public IslandSchema[] getSchemata() {
        IslandSchema aislandschema[] = new IslandSchema[schemata.size()];
        schemata.values().toArray(aislandschema);
        return aislandschema;
    }

    protected final Map schemata = new HashMap();
}
