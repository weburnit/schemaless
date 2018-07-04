package com.weburnit.schemaless.schema.document;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.RuleConstrains;
import com.weburnit.schemaless.schema.SchemaAssertionException;
import java.util.List;

public class RuleElement {

    private String field;

    private List<RuleConstrains> constrains;

    public RuleElement(String field, List<RuleConstrains> constrains) {
        this.field = field;
        this.constrains = constrains;
    }

    public String field() {
        return this.field;
    }

    public JsonNode read(JsonNode source) throws SchemaAssertionException {
        boolean allMatched = true;
        if (this.constrains.size() > 0) {
            allMatched = this.constrains.stream()
                .allMatch(constrains -> constrains.isValid(source, this.field));
        }
        if (!allMatched) {
            throw new SchemaAssertionException("Data doesn't match constrains");
        }
        return source;
    }

    private void bindRequired(JsonNode source) {

    }

    private void bindConstraints(JsonNode source) {

    }
}
