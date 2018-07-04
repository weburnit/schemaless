package com.weburnit.schemaless.schema.document.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.RuleConstrains;

public class MinLength implements RuleConstrains {

    int length;

    public MinLength(JsonNode length) {
        this.length = length.intValue();
    }

    @Override
    public boolean isValid(JsonNode node, String field) {
        if (node.textValue().length() < this.length) {
            return false;
        }
        return true;
    }
}
