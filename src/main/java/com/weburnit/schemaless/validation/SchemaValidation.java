package com.weburnit.schemaless.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.weburnit.schemaless.repository.ModellingRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SchemaValidation implements Validator {

    ModellingRepository repository;

    public SchemaValidation(ModellingRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JsonNode.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ObjectNode node = (ObjectNode) o;
        String name = node.get("name").asText();
        if (name == null) {
            errors.rejectValue("schema.name", "Schema must provide name");
            return;
        }
        if (this.repository.findByName(name).isPresent()) {
            errors.rejectValue("name", name, String.format("Schema with name %s",
                name));
        }
    }
}
