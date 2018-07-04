package com.weburnit.schemaless.schema.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.ElementReaderInterface;
import com.weburnit.schemaless.schema.SchemaAssertionException;
import java.rmi.activation.UnknownObjectException;

public class StringElement extends AbstractElement {

    public StringElement(String field) {
        super(field);
    }

    @Override
    public void bind(JsonNode source, ElementReaderInterface reader) throws UnknownObjectException {
        this.verify(reader, source, "string");
    }

    @Override
    public Object read(JsonNode source) throws SchemaAssertionException {
        JsonNode node = (JsonNode) super.read(source);

        return node.textValue();
    }
}
