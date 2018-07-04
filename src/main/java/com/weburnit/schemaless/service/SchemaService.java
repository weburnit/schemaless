package com.weburnit.schemaless.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.model.Attribute;
import com.weburnit.schemaless.model.Instance;
import com.weburnit.schemaless.model.Modelling;
import com.weburnit.schemaless.repository.InstanceRepository;
import com.weburnit.schemaless.repository.ModellingRepository;
import com.weburnit.schemaless.schema.ElementPrototype.SourcingInterface;
import com.weburnit.schemaless.schema.ElementReaderInterface;
import com.weburnit.schemaless.schema.SchemaAssertionException;
import com.weburnit.schemaless.schema.SchemaDocument;
import java.rmi.activation.UnknownObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class SchemaService {

    @Autowired
    ModellingRepository repository;

    @Autowired
    InstanceRepository instanceRepository;

    @Autowired
    ElementReaderInterface reader;

    public Modelling createModel(JsonNode data) throws UnknownObjectException {
        SchemaDocument document = new SchemaDocument(data.get("name").asText());
        document.bind(data.get("schema"), reader);
        return repository.save(
            new Modelling(document.getName(), document.getSource(), System.currentTimeMillis()));
    }

    public Instance createInstance(JsonNode data, String modelId)
        throws UnknownObjectException, SchemaAssertionException {
        Optional<Modelling> modelling = repository.findById(UUID.fromString(modelId));
        SchemaDocument document = new SchemaDocument(modelling.get().getName());

        List<Attribute> attributes = new ArrayList<>();
        document.bind(modelling.get().getBody(), reader);
        Instance instance = new Instance(modelling.get());
        document.extract(data, new SourcingInterface() {
            @Override
            public void binding(String key, Object value) {
                attributes.add(new Attribute(instance, key, value));
            }
        }, null);

        instance.setAttributes(attributes);
        instanceRepository.save(instance);
        return instance;
    }
}
