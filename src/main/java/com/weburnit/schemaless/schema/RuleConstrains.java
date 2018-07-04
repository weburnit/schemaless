package com.weburnit.schemaless.schema;

import com.fasterxml.jackson.databind.JsonNode;

public interface RuleConstrains {

    boolean isValid(JsonNode node, String field);
}
