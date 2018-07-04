package com.weburnit.schemaless.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.schema.document.NumericElement;
import com.weburnit.schemaless.schema.document.ObjectElement;
import com.weburnit.schemaless.schema.document.StringElement;
import com.weburnit.schemaless.schema.document.rules.MaxLength;
import com.weburnit.schemaless.schema.document.rules.Maximum;
import com.weburnit.schemaless.schema.document.rules.MinLength;
import com.weburnit.schemaless.schema.document.rules.Minimum;
import com.weburnit.schemaless.schema.document.rules.MultipleOf;
import com.weburnit.schemaless.schema.document.rules.Patterns;
import java.rmi.activation.UnknownObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ElementReaderInterfaceImpl implements ElementReaderInterface {

    private static final Map<String, ElementBuilderFunction<String, ElementPrototype>> SCHEMAS = new HashMap<>();

    private static final Map<String, ConstraintBuilderFunction<JsonNode, RuleConstrains>> CONSTRAINS = new HashMap<>();

    static {
        SCHEMAS.put("object", ObjectElement::new);
        SCHEMAS.put("string", StringElement::new);
        SCHEMAS.put("integer", NumericElement::new);

        CONSTRAINS.put("minimum", Minimum::new);
        CONSTRAINS.put("maximum", Maximum::new);
        CONSTRAINS.put("multipleOf", MultipleOf::new);
        CONSTRAINS.put("maxLength", MaxLength::new);
        CONSTRAINS.put("minLength", MinLength::new);
        CONSTRAINS.put("pattern", Patterns::new);
    }


    @Override
    public ElementPrototype read(JsonNode node, String field) throws UnknownObjectException {
        ElementBuilderFunction<String, ElementPrototype> builder = SCHEMAS
            .get(node.get("type").asText().toLowerCase());
        if (builder == null) {
            throw new UnknownObjectException(
                String.format("Unsupported type: %s for json %s", field, node.toString()));
        }
        ElementPrototype element = builder.apply(field);
        element.bind(node, this);

        return element;
    }

    @Override
    public List<RuleConstrains> validation(JsonNode node) {
        List<RuleConstrains> rules = new ArrayList<>();
        if (node != null) {
            for (Iterator<Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
                Entry<String, JsonNode> entry = it.next();

                ConstraintBuilderFunction<JsonNode, RuleConstrains> builder = CONSTRAINS
                    .get(entry.getKey());
                if (builder != null) {
                    rules.add(builder.apply(entry.getValue()));
                }
            }
        }

        return rules;
    }
}
