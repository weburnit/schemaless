package com.weburnit.schemaless.schema.document.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.RuleConstrains;

public class Minimum implements RuleConstrains {

    public int minimum;

    public Minimum(JsonNode minimum) {
        this.minimum = minimum.intValue();
    }

    @Override
    public boolean isValid(JsonNode node, String field) {
        if (node.intValue() < minimum) {
            return false;
        }
        return true;
    }
}
