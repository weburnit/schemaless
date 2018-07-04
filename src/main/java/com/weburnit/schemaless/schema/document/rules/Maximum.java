package com.weburnit.schemaless.schema.document.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.RuleConstrains;

public class Maximum implements RuleConstrains {

    public int maximum;

    public Maximum(JsonNode maximum) {
        this.maximum = maximum.intValue();
    }

    @Override
    public boolean isValid(JsonNode node, String field) {
        if (node.intValue() > maximum) {
            return false;
        }
        return true;
    }
}
