package com.weburnit.schemaless.schema.document.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.RuleConstrains;

public class Patterns implements RuleConstrains {

    String pattern;

    public Patterns(JsonNode pattern) {
        this.pattern = pattern.textValue();
    }

    @Override
    public boolean isValid(JsonNode node, String field) {
        if (node.textValue().matches(pattern)) {
            return true;
        }
        return false;
    }
}
