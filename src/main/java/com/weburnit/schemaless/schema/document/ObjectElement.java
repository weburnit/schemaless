package com.weburnit.schemaless.schema.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.weburnit.schemaless.schema.ElementPrototype;
import com.weburnit.schemaless.schema.ElementReaderInterface;
import com.weburnit.schemaless.schema.SchemaAssertionException;
import java.rmi.activation.UnknownObjectException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ObjectElement extends AbstractElement {

    protected transient HashMap<String, ElementPrototype> properties;


    public ObjectElement(String field) {
        super(field);
        this.properties = new HashMap<>();
    }

    @Override
    public void bind(JsonNode source, ElementReaderInterface reader) throws UnknownObjectException {
        this.verify(reader, source, "object");
        for (Iterator<Entry<String, JsonNode>> it = source.get("properties").fields();
            it.hasNext(); ) {
            Entry<String, JsonNode> entry = it.next();
            ElementPrototype element = reader.read(entry.getValue(), entry.getKey());
            properties.put(entry.getKey(), element);
        }
    }

    @Override
    public Object read(JsonNode source) throws SchemaAssertionException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode data = mapper.createObjectNode();
        for (Entry<String, ElementPrototype> entry : properties.entrySet()) {
            Object node = entry.getValue().read(source.get(entry.getKey()));
            data.putPOJO(entry.getKey(), node);
        }
        return source;
    }

    @Override
    public Object extract(JsonNode request, SourcingInterface sourcing, String root)
        throws SchemaAssertionException {
        if (root == null) {
            root = this.field;
        } else {
            root = String.format("%s.%s", root, this.field);
        }
        for (Entry<String, ElementPrototype> entry : properties.entrySet()) {
            JsonNode source = request.get(entry.getKey());
            if (source != null) {
                sourcing
                    .binding(String.format("%s.%s", root, entry.getKey()),
                        entry.getValue().read(source));
            }
        }
        return null;
    }
}
