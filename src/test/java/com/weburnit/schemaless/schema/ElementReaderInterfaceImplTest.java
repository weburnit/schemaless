package com.weburnit.schemaless.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.weburnit.schemaless.schema.document.ObjectElement;
import java.rmi.activation.UnknownObjectException;
import org.junit.Before;
import org.junit.Test;

public class ElementReaderInterfaceImplTest {

    private ElementReaderInterfaceImpl reader;
    private ObjectMapper mapper;
    private ObjectNode node;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        reader = new ElementReaderInterfaceImpl();
        node = mapper.createObjectNode();
        node.put("type", "object");

        ObjectNode properties = mapper.createObjectNode();

        ObjectNode age = mapper.createObjectNode();
        age.put("type", "integer");
        age.put("minimum", 1);
        age.put("maximum", 100);
        properties.set("age", age);

        ObjectNode name = mapper.createObjectNode();
        name.put("type", "string");
        name.put("minLength", 3);
        name.put("maxLength", 256);
        properties.set("name", name);

        ObjectNode salary = mapper.createObjectNode();
        salary.put("type", "integer");
        salary.put("multipleOf", 10);
        properties.set("salary", salary);

        node.set("properties", properties);
    }

    @Test
    public void readObject() throws UnknownObjectException {
        ElementPrototype object = reader.read(node, "name");
        assert object instanceof ObjectElement;
        assertEquals("name", object.field());
    }

    @Test
    public void validation() throws Exception {
        ObjectNode data = mapper.createObjectNode();
        data.put("age", 100);
        data.put("name", "Paul");
        data.put("salary", 4020);

        ElementPrototype object = reader.read(node, "people");
        Object result = object.read(data);
        assertNotNull(result);
    }
}