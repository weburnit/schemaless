package com.weburnit.schemaless.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.document.ObjectElement;
import java.rmi.activation.UnknownObjectException;

public class SchemaDocument extends ObjectElement {

    private String name;

    private JsonNode source;

    public SchemaDocument(String name) {
        super("schema");
        this.name = name;
    }

    @Override
    public void bind(JsonNode source, ElementReaderInterface reader) throws UnknownObjectException {
        this.source = source;
        super.bind(source, reader);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return source.toString();
    }

    public JsonNode getSource() {
        return source;
    }

}
