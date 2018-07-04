package com.weburnit.schemaless.schema.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.tools.javac.util.Assert;
import com.weburnit.schemaless.schema.ElementPrototype;
import com.weburnit.schemaless.schema.ElementReaderInterface;
import com.weburnit.schemaless.schema.RuleConstrains;
import com.weburnit.schemaless.schema.SchemaAssertionException;
import java.rmi.activation.UnknownObjectException;
import java.util.List;

public abstract class AbstractElement implements ElementPrototype {

    protected transient String field;

    private transient RuleElement rule;

    public AbstractElement(String field) {
        this.field = field;
    }

    public RuleElement rules() {
        return rule;
    }

    @Override
    public String field() {
        return field;
    }

    @Override
    public Object read(JsonNode source) throws SchemaAssertionException {
        return this.rules().read(source);
    }

    final void verify(ElementReaderInterface reader, JsonNode source, String type)
        throws UnknownObjectException {
        try {
            Assert.check(source.get("type").asText().toLowerCase().equals(type));
        } catch (AssertionError error) {
            throw new UnknownObjectException(
                String.format("Unknown format %s for %s", type, source.toString()));
        }

        List<RuleConstrains> constrains = reader.validation(source);
        rule = new RuleElement(this.field, constrains);
    }

    public Object extract(JsonNode request, SourcingInterface sourcing, String root)
        throws SchemaAssertionException {
        Object data = this.read(request);
        if (root == null) {
            root = this.field;
        } else {
            root = String.format("%s.%s", root, this.field);
        }
        sourcing.binding(root, data);
        return data;
    }
}
