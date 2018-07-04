package com.weburnit.schemaless.schema;

import com.fasterxml.jackson.databind.JsonNode;
import java.rmi.activation.UnknownObjectException;
import java.util.List;

public interface ElementReaderInterface {

    ElementPrototype read(JsonNode node, String field) throws UnknownObjectException;

    List<RuleConstrains> validation(JsonNode node);
}
