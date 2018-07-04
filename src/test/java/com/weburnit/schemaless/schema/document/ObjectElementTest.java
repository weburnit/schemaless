package com.weburnit.schemaless.schema.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.weburnit.schemaless.schema.ElementPrototype;
import com.weburnit.schemaless.schema.ElementReaderInterface;
import java.rmi.activation.UnknownObjectException;
import org.junit.Before;
import org.junit.Test;

public class ObjectElementTest {

    ObjectElement element;

    JsonNode node;

    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        element = new ObjectElement("life");
        mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "object");

        ObjectNode properties = mapper.createObjectNode();

        ObjectNode age = mapper.createObjectNode();
        age.put("type", "numeric");
        properties.set("age", age);

        ObjectNode name = mapper.createObjectNode();
        name.put("type", "String");
        properties.set("name", name);

        node.set("properties", properties);
        this.node = node;
    }

    @Test
    public void field() {
        assertEquals("life", element.field());
    }

    @Test
    public void giveJsonString_Parse() throws Exception {
        ElementReaderInterface reader = mock(ElementReaderInterface.class);
        when(reader.read(any(JsonNode.class), anyString()))
            .thenReturn(mock(ElementPrototype.class));
        element.bind(node, reader);
        verify(reader)
            .read(
                argThat(item -> item.get("type").asText().equals("numeric")),
                eq("age"));
        verify(reader)
            .read(
                argThat(item -> item.get("type").asText().equals("String")),
                eq("name"));
    }

    @Test
    public void bind() throws Exception {
        ElementReaderInterface reader = mock(ElementReaderInterface.class);
        when(reader.read(any(JsonNode.class), anyString()))
            .thenReturn(mock(ElementPrototype.class));
        element.bind(node, reader);

        ObjectNode dataBinding = mapper.createObjectNode();
        dataBinding.put("age", 12);
        dataBinding.put("name", "Paul");
        Object result = element.read(dataBinding);
        assertNotNull(result);
    }

    @Test(expected = UnknownObjectException.class)
    public void bind_havingException() throws UnknownObjectException {
        ElementReaderInterface reader = mock(ElementReaderInterface.class);
        when(reader.read(any(JsonNode.class), anyString()))
            .thenReturn(mock(ElementPrototype.class));
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "array");
        element.bind(node, reader);
    }
}