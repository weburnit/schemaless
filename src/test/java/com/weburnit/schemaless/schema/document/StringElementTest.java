package com.weburnit.schemaless.schema.document;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.weburnit.schemaless.schema.ElementReaderInterface;
import com.weburnit.schemaless.schema.RuleConstrains;
import com.weburnit.schemaless.schema.SchemaAssertionException;
import java.rmi.activation.UnknownObjectException;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

public class StringElementTest {

    StringElement element;

    JsonNode node;

    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        element = new StringElement("age");
        mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "string");
        this.node = node;
    }

    @Test
    public void bind() throws UnknownObjectException {
        ElementReaderInterface reader = mock(ElementReaderInterface.class);

        when(reader.validation(node)).thenReturn(Collections.emptyList());
        element.bind(node, reader);

        assertEquals("age", element.rules().field());
    }

    @Test
    public void read() throws Exception {
        ElementReaderInterface reader = mock(ElementReaderInterface.class);

        ObjectNode data = mapper.createObjectNode();
        data.put("age", "12");
        RuleConstrains min = mock(RuleConstrains.class);
        when(min.isValid(any(TextNode.class), eq("age"))).thenReturn(true);
        when(reader.validation(any(JsonNode.class)))
            .thenReturn(Collections.singletonList(min));
        element.bind(node, reader);
        Object value = element.read(new TextNode("12"));

        assertEquals("12", value);
    }

    @Test(expected = SchemaAssertionException.class)
    public void givenMinimumConstrains_isInvalid() throws Exception {
        ElementReaderInterface reader = mock(ElementReaderInterface.class);

        ObjectNode data = mapper.createObjectNode();
        data.put("age", "24");
        RuleConstrains min = mock(RuleConstrains.class);

        when(min.isValid(data, "age")).thenReturn(false);

        when(reader.validation(node)).thenReturn(Collections.singletonList(min));
        element.bind(node, reader);
        element.read(data);
    }
}