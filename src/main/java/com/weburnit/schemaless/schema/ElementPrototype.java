package com.weburnit.schemaless.schema;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.Serializable;
import java.rmi.activation.UnknownObjectException;

public interface ElementPrototype extends Serializable {

    public interface SourcingInterface {

        void binding(String key, Object value);
    }
    public Object extract(JsonNode request, SourcingInterface sourcing, String root)
        throws SchemaAssertionException;

    void bind(JsonNode source, ElementReaderInterface reader) throws UnknownObjectException;

    String field();

    Object read(JsonNode source) throws SchemaAssertionException;
}
