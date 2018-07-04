package com.weburnit.schemaless.schema.document.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.RuleConstrains;

public class MultipleOf implements RuleConstrains {

    private int module;

    public MultipleOf(JsonNode module) {
        this.module = module.intValue();
    }

    @Override
    public boolean isValid(JsonNode node, String field) {
        if (node.intValue() % this.module > 0) {
            return false;
        }
        return true;
    }
}
